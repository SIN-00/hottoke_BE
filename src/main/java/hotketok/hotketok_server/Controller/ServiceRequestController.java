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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseEntity<Map<String, String>> postServiceRequest(
            @RequestBody Map<String, Object> requestBody,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByEmail(loginId);

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
                .requestStatus("PENDING")
                .houseUserMapping(houseUserMapping)
                .createdAt(LocalDateTime.now())
                .status("ACTIVE")
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
        response.put("requestId", serviceRequest.getRequestId().toString());
        return ResponseEntity.ok(response);
    }

    // 진행중인 요청서 조회
    @GetMapping("/service/progressing")
    public ResponseEntity<List<ServiceRequest>> progressServiceRequest(@AuthenticationPrincipal CustomUserDetails userDetails) {

        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByEmail(loginId);
        return null;
    }
}
