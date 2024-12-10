package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.CustomUserDetails;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.UserRepository;
import hotketok.hotketok_server.Service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;
    private final UserRepository userRepository;

    @GetMapping("/search-address")
    public String searchAddress(@RequestParam String keyword) {
        return houseService.getRawJsonResponse(keyword);
    }

    @PostMapping("/address/select")
    public ResponseEntity<Map<String, String>> selectAddress(@RequestBody Map<String, String> requestBody, @AuthenticationPrincipal CustomUserDetails userDetails) {

        String loginId = userDetails.getUser().getLoginId();
        System.out.println("controller: Email: " + userDetails.getUser());
        User user = userRepository.findByEmail(loginId);
        System.out.println("controller: user: " + user);
        // * 특이사항 * userDetails에서 로그인Id를 가져와서 유저에서 이를 가지고 이메일을 가져옴 -> userDetails 에서는 로그인 아이디가 이메일로 저장됨
        // 출력 결과
        // controller: Email: User(id=null, username=null, role=null, loginId=null, email=abc, password=null, status=null, profile_image=null)
        // controller: user: User(id=1, username=abc, role=null, loginId=abc, email=null, password=$2a$10$LEtbtaa9WbmJi3cxzXnDW.QFW.H91oHTtEGPFlR/uiq8j6OoE7YcG, status=null, profile_image=null)

        String address = requestBody.get("address");
        String unit_number = requestBody.get("unit_number");

        houseService.selectAddress(address, unit_number, user);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
