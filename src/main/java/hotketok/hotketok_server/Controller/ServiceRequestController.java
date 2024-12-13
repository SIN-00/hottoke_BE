package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.CustomUserDetails;
import hotketok.hotketok_server.Domain.ConstructionDate;
import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.ServiceRequest;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
import hotketok.hotketok_server.Repository.ServiceRequestRepository;
import hotketok.hotketok_server.Repository.UserRepository;
import hotketok.hotketok_server.Service.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Map<String, String>> postServiceRequest(@RequestBody Map<String, Object> requestBody, @AuthenticationPrincipal CustomUserDetails userDetails) {

        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByLoginId(loginId);

        HouseUserMapping houseUserMapping = houseUserMappingRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 매핑 정보를 찾을 수 없습니다."));

        String category = (String) requestBody.get("category");
        String requestImage = (String) requestBody.get("request_image");
        String requestDescription = (String) requestBody.get("request_description");

        // `construction_date` 데이터 파싱
        List<Map<String, Object>> constructionDatesInput = (List<Map<String, Object>>) requestBody.get("construction_date");

        ServiceRequest serviceRequest = ServiceRequest.builder()
                .category(category)
                .requestImage(requestImage)
                .requestDescription(requestDescription)
                .houseUserMapping(houseUserMapping)
                .createdAt(LocalDateTime.now())
                .status(0) // 업체 찾는 중 초기 세팅
                .build();

        // `constructionDates` 초기화 확인
        if (serviceRequest.getConstructionDates() == null) {
            serviceRequest.setConstructionDates(new ArrayList<>());
        }

        // ConstructionDate 추가
        for (Map<String, Object> dateEntry : constructionDatesInput) {
            LocalDate date = LocalDate.parse((String) dateEntry.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // 안전한 타입 캐스팅
            List<String> times = new ArrayList<>();
            Object timesObject = dateEntry.get("times");
            if (timesObject instanceof List<?>) {
                times = ((List<?>) timesObject).stream()
                        .filter(item -> item instanceof String)
                        .map(String.class::cast)
                        .collect(Collectors.toList());
            }

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
    public ResponseEntity<List<Map<String, Long>>> progressServiceRequest(@AuthenticationPrincipal CustomUserDetails userDetails) {

        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByLoginId(loginId);

        // 진행중인 요청서 가져오기
        List<ServiceRequest> progressRequests = serviceRequestService.progressServiceRequest(user);

        List<Map<String, Long>> response = progressRequests.stream() // 여러 개 일 수 있음
                .map(request -> {
                    Map<String, Long> map = new HashMap<>();
                    map.put("request_id", request.getRequestId());
                    return map;
                })
                .toList();

        return ResponseEntity.ok(response);
    }

    // 완료된 요청서 조회
    @GetMapping("/service/done")
    public ResponseEntity<List<Map<String, Long>>> doneServiceRequest(@AuthenticationPrincipal CustomUserDetails userDetails) {

        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByLoginId(loginId);

        // 완료된 요청서 가져오기
        List<ServiceRequest> progressRequests = serviceRequestService.doneServiceRequest(user);

        List<Map<String, Long>> response = progressRequests.stream() // 여러 개 일 수 있음
                .map(request -> {
                    Map<String, Long> map = new HashMap<>();
                    map.put("request_id", request.getRequestId());
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
