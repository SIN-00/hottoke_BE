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
        User user = userRepository.findByLoginId(loginId);

        String address = requestBody.get("address");
        String unit_number = requestBody.get("unit_number");

        houseService.selectAddress(address, unit_number, user);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
