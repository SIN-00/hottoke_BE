package hotketok.hotketok_server.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hotketok.hotketok_server.DTO.CustomUserDetails;
import hotketok.hotketok_server.Domain.*;
import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
import hotketok.hotketok_server.Repository.ServiceRequestRepository;
import hotketok.hotketok_server.Repository.UserRepository;
import hotketok.hotketok_server.Repository.VendorRequestMappingRepository;
import hotketok.hotketok_server.Service.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ServiceRequestController {

    private final UserRepository userRepository;
    private final HouseUserMappingRepository houseUserMappingRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ServiceRequestService serviceRequestService;

    // 요청서 작성
    @PostMapping("/service")
    public ResponseEntity<Map<String, String>> postServiceRequest(
            @RequestBody Map<String, Object> requestBody,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String baseUrl = "https://api.hotketok.store/";

        // 로그인한 유저 정보 확인
        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByLoginId(loginId);

        HouseUserMapping houseUserMapping = houseUserMappingRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 매핑 정보를 찾을 수 없습니다."));

        // 요청서 데이터 처리
        String category = (String) requestBody.get("category");
        List<String> requestImages = (List<String>) requestBody.get("request_image");
        String requestDescription = (String) requestBody.get("request_description");

        // request_image를 URL로 변환
        List<String> imageUrls = requestImages.stream()
                .map(fileName -> baseUrl + fileName) // 파일명에 기본 URL을 추가
                .toList();

        // 변환된 URL 리스트를 JSON 문자열로 직렬화
        String requestImageJson = null;
        try {
            requestImageJson = new ObjectMapper().writeValueAsString(imageUrls);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize request images", e);
        }

        List<Map<String, Object>> constructionDatesInput = (List<Map<String, Object>>) requestBody.get("construction_date");

        ServiceRequest serviceRequest = ServiceRequest.builder()
                .category(category)
                .requestImage(requestImageJson) // 변환된 JSON 문자열 저장
                .requestDescription(requestDescription)
                .houseUserMapping(houseUserMapping)
                .createdAt(LocalDateTime.now())
                .status(0) // 초기 상태
                .build();

        // ConstructionDate 추가
        for (Map<String, Object> dateEntry : constructionDatesInput) {
            LocalDate date = LocalDate.parse((String) dateEntry.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            List<String> times = ((List<?>) dateEntry.get("times")).stream()
                    .filter(item -> item instanceof String)
                    .map(String.class::cast)
                    .collect(Collectors.toList());

            ConstructionDate constructionDate = ConstructionDate.builder()
                    .date(date)
                    .times(times)
                    .serviceRequest(serviceRequest)
                    .build();

            serviceRequest.getConstructionDates().add(constructionDate);
        }

        serviceRequestRepository.save(serviceRequest);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    // 진행중인 요청서 조회
    @GetMapping("/service/progressing")
    public ResponseEntity<List<Map<String, Object>>> progressServiceRequest(@AuthenticationPrincipal CustomUserDetails userDetails) {

        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByLoginId(loginId);

        // 진행중인 요청서 가져오기
        List<Map<String, Object>> response = serviceRequestService.progressServiceRequest(user);

        return ResponseEntity.ok(response);
    }

    // 완료된 요청서 조회
    @GetMapping("/service/done")
    public ResponseEntity<List<Map<String, String>>> doneServiceRequest(@AuthenticationPrincipal CustomUserDetails userDetails) {

        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByLoginId(loginId);

        // 완료된 요청서 가져오기
        List<ServiceRequest> progressRequests = serviceRequestService.doneServiceRequest(user);

        List<Map<String, String>> response = progressRequests.stream() // 여러 개 일 수 있음
                .map(request -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("request_id", String.valueOf(request.getRequestId()));
                    map.put("category", request.getCategory());
                    map.put("date", String.valueOf(request.getCreatedAt())); // 웹페이지 기능 구현 후 수정 필요
                    map.put("construction_vendor", "메종 인테리어");
                    return map;
                })
                .toList();

        return ResponseEntity.ok(response);
    }

    // 수리/공사 요청서 조회
    @GetMapping("/service-request")
    public ResponseEntity<Map<String, Object>> getServiceRequest(@RequestParam("request_id") Long requestId, @AuthenticationPrincipal CustomUserDetails userDetails) {

        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByLoginId(loginId);

        Map<String, Object> response = serviceRequestService.getServiceRequest(requestId, user);
        return ResponseEntity.ok(response);
    }

    // 수리/공사 요청서 삭제
    @DeleteMapping("/service-request")
    public ResponseEntity<Map<String, String>> deleteRequest(@RequestParam("request_id") Long requestId, @AuthenticationPrincipal CustomUserDetails userDetails) {

        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByLoginId(loginId);

        serviceRequestService.deleteServiceRequest(requestId, user);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
