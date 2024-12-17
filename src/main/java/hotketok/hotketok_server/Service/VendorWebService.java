package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.Domain.*;
import hotketok.hotketok_server.Repository.ConstructionVendorRepository;
import hotketok.hotketok_server.Repository.ServiceRequestRepository;
import hotketok.hotketok_server.Repository.VendorRequestMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VendorWebService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final VendorRequestMappingRepository vendorRequestMappingRepository;
    private final ConstructionVendorRepository constructionVendorRepository;

    // 수리 요청 조회
    public List<Map<String, Object>> getServiceRequest() {

        // 완료되지 않은 요청서 조회
        List<ServiceRequest> serviceRequests = serviceRequestRepository.findByStatus(0);

        List<Map<String, Object>> response = new ArrayList<>();

        for (ServiceRequest request : serviceRequests) {
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("request_id", request.getRequestId());
            requestMap.put("category", request.getCategory());
            requestMap.put("created_at", request.getCreatedAt().toString());
            requestMap.put("address", request.getHouseUserMapping().getHouse().getHouseAddress());
            requestMap.put("request_image", request.getRequestImage());
            requestMap.put("request_description", request.getRequestDescription());

            // 스케줄 데이터를 key: 날짜, value: 시간으로 설정
            Map<String, List<String>> scheduleMap = new HashMap<>();
            for (ConstructionDate schedule : request.getConstructionDates()) {
                scheduleMap.put(schedule.getDate().toString(), schedule.getTimes());
            }
            requestMap.put("request_schedule", scheduleMap);

            response.add(requestMap);
        }

        return response;
    }

    // 진행 중인 수리 조회
    @Transactional
    public List<Map<String, Object>> getProgressingService() {

        // 공사업체 ID와 status로 매핑 데이터 조회
        List<VendorRequestMapping> mappings = vendorRequestMappingRepository.findByVendorVendorIdAndRequestStatus(1L, 2);

        List<Map<String, Object>> response = new ArrayList<>();

        for (VendorRequestMapping mapping : mappings) {
            ServiceRequest request = mapping.getRequest();

            // 요청서 정보 추가
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("request_id", request.getRequestId());
            requestMap.put("category", request.getCategory());
            requestMap.put("created_at", request.getCreatedAt().toString());
            requestMap.put("address", request.getHouseUserMapping().getHouse().getHouseAddress());
            requestMap.put("request_image", request.getRequestImage());
            requestMap.put("request_description", request.getRequestDescription());

            // 스케줄 데이터를 JSON 형태로 변환
            Map<String, List<String>> scheduleMap = new HashMap<>();
            for (ConstructionDate schedule : request.getConstructionDates()) {
                scheduleMap.put(schedule.getDate().toString(), schedule.getTimes());
            }
            requestMap.put("request_schedule", scheduleMap);

            response.add(requestMap);
        }

        return response;
    }

    // 견적서 작성
    public String postEstimate(Long requestId, String estimate_price, String estimate_time, String additional_comment) {

        // VendorRequestMapping 조회
        ServiceRequest serviceRequest = serviceRequestRepository.findByRequestId(requestId);

        if (serviceRequest == null) {
            throw new IllegalArgumentException("해당 요청서를 찾을 수 없습니다.");
        }

        Long vendorId = 1L; // 이후 수정 필요.
        ConstructionVendor vendor = (ConstructionVendor) constructionVendorRepository.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공사업체를 찾을 수 없습니다."));

        // VendorRequestMapping 튜플 생성
        VendorRequestMapping vendorRequestMapping = VendorRequestMapping.builder()
                .request(serviceRequest)
                .vendor(vendor)
                .estimatePrice(estimate_price)
                .estimateTime(estimate_time)
                .additionalComment(additional_comment)
                .build();

        vendorRequestMappingRepository.save(vendorRequestMapping);

        // 요청서 상태 변경
        serviceRequest.setStatus(1);
        System.out.println(serviceRequest.getRequestId());
        serviceRequestRepository.save(serviceRequest);

        return "success";
    }
}
