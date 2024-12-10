package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.CustomUserDetails;
import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.ServiceRequest;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
import hotketok.hotketok_server.Repository.ServiceRequestRepository;
import hotketok.hotketok_server.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    // 요청서 작성
    @PostMapping("/service")
    public ResponseEntity<Map<String, String>> postServiceRequest(
            @RequestBody Map<String, Object> requestBody,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByEmail(loginId);

        // [하우스유저매핑]과 연결
        HouseUserMapping houseUserMapping = houseUserMappingRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 매핑 정보를 찾을 수 없습니다."));

        String category = (String) requestBody.get("category");
        String requestImage = (String) requestBody.get("request_image");
        String requestDescription = (String) requestBody.get("request_description");
        // 스케줄 데이터 파싱
        List<Map<String, Object>> constructionDates = (List<Map<String, Object>>) requestBody.get("construction_date");
        List<LocalDateTime> parsedDates = new ArrayList<>();

        for (Map<String, Object> dateEntry : constructionDates) {
            String date = (String) dateEntry.get("date"); // 날짜 추출
            List<String> times = (List<String>) dateEntry.get("times"); // 시간 목록 추출

            for (String time : times) {
                LocalDateTime dateTime = LocalDateTime.parse(
                        date + " " + time,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                );
                parsedDates.add(dateTime);
            }
        }

        // 요청서 저장
        for (LocalDateTime constructionDate : parsedDates) {
            ServiceRequest serviceRequest = ServiceRequest.builder()
                    .category(category)
                    .requestImage(requestImage)
                    .requestDescription(requestDescription)
                    .constructionDate(constructionDate)
                    .houseUserMapping(houseUserMapping)
                    .createdAt(LocalDateTime.now())
                    .status("업체 찾는 중") // 요청서 만들어지면 초기 값
                    .build();

            serviceRequestRepository.save(serviceRequest);
        }

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
