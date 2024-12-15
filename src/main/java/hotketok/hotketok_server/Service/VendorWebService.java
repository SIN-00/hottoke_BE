package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.Domain.ServiceRequest;
import hotketok.hotketok_server.Repository.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VendorWebService {

    private final ServiceRequestRepository serviceRequestRepository;

    // 수리 요청 조회
//    public List<Map<String, Object>> getServiceRequest() {
//
//        // 완료되지 않은 요청서 조회
//    }
}
