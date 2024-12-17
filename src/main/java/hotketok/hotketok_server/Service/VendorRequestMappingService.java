package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.ServiceRequest;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Domain.VendorRequestMapping;
import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
import hotketok.hotketok_server.Repository.ServiceRequestRepository;
import hotketok.hotketok_server.Repository.VendorRequestMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendorRequestMappingService {

    private final VendorRequestMappingRepository vendorRequestMappingRepository;
    private final ServiceRequestRepository serviceRequestRepository;

    // 견적서 조회
    public Map<String, Map<String, Object>> getEstimate(Long requestId, User user) {

        // ServiceRequest 조회
        ServiceRequest serviceRequest = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("해당 요청서를 찾을 수 없습니다."));

        // VendorRequestMapping 조회
        List<VendorRequestMapping> vendorRequestMappings = vendorRequestMappingRepository.findByRequest(serviceRequest);

        Map<String, Map<String, Object>> estimates = new HashMap<>();
        for (VendorRequestMapping mapping : vendorRequestMappings) {
            Map<String, Object> estimateDetails = new HashMap<>();
            estimateDetails.put("vendor_name", mapping.getVendor().getVendorName());
            estimateDetails.put("call_number", mapping.getVendor().getCallNumber());
            estimateDetails.put("vendor_address", mapping.getVendor().getVendorAddress());
            estimateDetails.put("estimate_price", mapping.getEstimatePrice());
            estimateDetails.put("estimate_time", mapping.getEstimateTime());
            estimateDetails.put("additional_comment", mapping.getAdditionalComment());

            estimates.put(String.valueOf(mapping.getVendorRequestMappingId()), estimateDetails);
        }

        return estimates;
    }

    // 견적서 선택
    public String selectEstimate(Long requestId, Long estimateId) {

        // ServiceRequest 조회
        ServiceRequest serviceRequest = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("해당 요청서를 찾을 수 없습니다."));

        serviceRequest.setStatus(2); // 업체 매칭으로 변경

        VendorRequestMapping vendorRequestMapping = vendorRequestMappingRepository.findByVendorRequestMappingId(estimateId);
        vendorRequestMapping.setStatus(1);

        return "success";
    }
}

