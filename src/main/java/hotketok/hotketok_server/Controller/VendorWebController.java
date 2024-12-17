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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
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
    public ResponseEntity<List<Map<String, Object>>> getProgressingService() {

        List<Map<String, Object>> response = vendorWebService.getProgressingService();
        return ResponseEntity.ok(response);
    }

    // 견적서 작성
    @PostMapping("/vendor/estimate")
    public ResponseEntity<Map<String, String>> postEstimate(@RequestBody Map<String, Object> requestBody) {

        Long requestId = Long.parseLong(requestBody.get("request_id").toString());
        String estimatePrice = (String) requestBody.get("estimate_price");
        String estimateTime = (String) requestBody.get("estimate_time");
        String additionalComment = (String) requestBody.get("additional_comment");

        // 견적서 작성 처리 로직
        vendorWebService.postEstimate(requestId, estimatePrice, estimateTime, additionalComment);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
