package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.CustomUserDetails;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.UserRepository;
import hotketok.hotketok_server.Service.VendorRequestMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class VendorRequestMappingController {

    private final UserRepository userRepository;
    private final VendorRequestMappingService vendorRequestMappingService;

    // 견적서 조회
    @GetMapping("/estimate")
    public ResponseEntity<Map<String, Map<String, Object>>> getEstimate(@RequestParam("request_id") Long requestId, @AuthenticationPrincipal CustomUserDetails userDetails) {

        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByLoginId(loginId);

        Map<String, Map<String, Object>> response = vendorRequestMappingService.getEstimate(requestId, user);
        return ResponseEntity.ok(response);
    }

    // 견적서 선택
    @PostMapping("/estimate")
    public ResponseEntity<Map<String, String>> selectEstimate(@RequestParam("estimate_id") Long requestId, @AuthenticationPrincipal CustomUserDetails userDetails) {

        vendorRequestMappingService.selectEstimate(requestId);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
