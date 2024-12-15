package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.CustomUserDetails;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.ServiceRequestRepository;
import hotketok.hotketok_server.Repository.UserRepository;
import hotketok.hotketok_server.Service.VendorWebService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class VendorWebController {

    private final VendorWebService vendorWebService;
    private final UserRepository userRepository;

    // 수리 요청 조회
    @GetMapping("/vendor/search")
    public ResponseEntity<List<Map<String, Object>>> getServiceRequest() {

        List<Map<String, Object>> response = vendorWebService.getServiceRequest();
        return ResponseEntity.ok(response);
    }

    // 진행 중인 수리 조회
    @GetMapping("/vendor/status")
    public ResponseEntity<List<Map<String, Object>>> getProgressingService(@AuthenticationPrincipal CustomUserDetails userDetails) {

        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByLoginId(loginId);

        List<Map<String, Object>> response = vendorWebService.getProgressingService(user);
        return ResponseEntity.ok(response);
    }
}
