package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.CustomUserDetails;
import hotketok.hotketok_server.Repository.ServiceRequestRepository;
import hotketok.hotketok_server.Service.VendorWebService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class VendorWebController {

    private final VendorWebService vendorWebService;

    // 수리 요청 조회
    public ResponseEntity<List<Map<String, Object>>> getServiceRequest() {

        List<Map<String, Object>> response = vendorWebService.getServiceRequest();
        return ResponseEntity.ok(response);
    }
}
