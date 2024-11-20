package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.Domain.ServiceRequest;
import hotketok.hotketok_server.Service.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("service")
    public ResponseEntity<Map<String, String>> createRequest(@RequestBody ServiceRequest serviceRequest) {

        serviceRequestService.createRequest(serviceRequest);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
