package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.CustomUserDetails;
import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.Notice;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
import hotketok.hotketok_server.Repository.NoticeRepository;
import hotketok.hotketok_server.Repository.UserRepository;
import hotketok.hotketok_server.Service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final UserRepository userRepository;
    private final HouseUserMappingRepository houseUserMappingRepository;

    @GetMapping("/notice")
    public ResponseEntity<Map<String, Map<String, Object>>> getNotices(@AuthenticationPrincipal CustomUserDetails userDetails) {
        // 로그인된 유저 가져오기
        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByLoginId(loginId);

        System.out.println("user login Id : " + user.getLoginId());
        // 서비스에서 공지사항 데이터 가져오기
        Map<String, Map<String, Object>> response = noticeService.getUserNotices(user);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/my-status")
    public ResponseEntity<Map<String, String>> getMyStatus(@RequestParam String status, @AuthenticationPrincipal CustomUserDetails userDetails) {
        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByLoginId(loginId);

        user.setStatus(status);
        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-status")
    public ResponseEntity<Map<String, Map<String, String>>> getStatus(@AuthenticationPrincipal CustomUserDetails userDetails) {
        // 로그인된 유저 가져오기
        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByLoginId(loginId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 유저의 house 정보 가져오기
        Optional<HouseUserMapping> userMappings = houseUserMappingRepository.findByUser(user);
        if (userMappings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 해당 유저가 속한 모든 세대의 정보를 조회
        House house = userMappings.get().getHouse(); // 유저가 속한 첫 번째 집
        List<HouseUserMapping> mappings = houseUserMappingRepository.findAllByHouse(house);

        Map<String, Map<String, String>> response = new HashMap<>();

        for (HouseUserMapping mapping : mappings) {
            String unitNumber = mapping.getUnitNumber(); // `unitNumber`는 String이므로 그대로 사용

            // 해당 세대 번호의 유저 가져오기
            User resident = mapping.getUser();
            if (resident != null) {
                // 층별 데이터 생성
                String floorKey = unitNumber.substring(0, 1) + "층"; // 예: 101호 -> 1층
                Map<String, String> floorMap = response.getOrDefault(floorKey, new HashMap<>());

                // 세대별 상태 추가
                floorMap.put(unitNumber, resident.getStatus());
                response.put(floorKey, floorMap);
            }
        }

        return ResponseEntity.ok(response);
    }



}
