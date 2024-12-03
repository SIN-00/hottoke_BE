package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.CustomUserDetails;
import hotketok.hotketok_server.Domain.ServiceRequest;
import hotketok.hotketok_server.Service.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    // 수리/공사 요청서 작성
    @PostMapping("/service")
    public ResponseEntity<Map<String, String>> createRequest(@RequestBody ServiceRequest serviceRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUser().getUserId();
        serviceRequestService.createRequest(serviceRequest, userId);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    // 수리/공사 요청서 조회
    @GetMapping("/service")
    public ResponseEntity<Map<String, Map<String, Object>>> getRequests(@AuthenticationPrincipal CustomUserDetails userDetails) {

        Map<String, Map<String, Object>> requests = serviceRequestService.getRequests();
        return ResponseEntity.ok(requests);
    }
}
