package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.Domain.ConstructionDate;
import hotketok.hotketok_server.Domain.ServiceRequest;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Domain.VendorRequestMapping;
import hotketok.hotketok_server.Repository.ServiceRequestRepository;
import hotketok.hotketok_server.Repository.VendorRequestMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VendorWebService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final VendorRequestMappingRepository vendorRequestMappingRepository;

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
    public List<Map<String, Object>> getProgressingService(User user) {

        // 공사업체 ID와 status로 매핑 데이터 조회
        List<VendorRequestMapping> mappings = vendorRequestMappingRepository.findByVendorVendorIdAndRequestStatus(1L, 3);

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
}
