package hotketok.hotketok_server.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hotketok.hotketok_server.Domain.*;
import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
import hotketok.hotketok_server.Repository.ServiceRequestRepository;
import hotketok.hotketok_server.Repository.VendorRequestMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {

    private final HouseUserMappingRepository houseUserMappingRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final VendorRequestMappingRepository vendorRequestMappingRepository;

    // 진행중인 요청서 조회
    public List<Map<String, Object>> progressServiceRequest(User user) {

        // 진행 중인 요청서 가져오기 (상태가 0, 1, 2)
        List<ServiceRequest> progressRequests = serviceRequestRepository.findByStatusIn(List.of(0, 1, 2));

        List<Map<String, Object>> response = new ArrayList<>();

        for (ServiceRequest request : progressRequests) {
            Map<String, Object> map = new HashMap<>();
            map.put("request_id", request.getRequestId());
            map.put("category", request.getCategory());
            map.put("date", String.valueOf(request.getCreatedAt())); // 웹페이지 기능 구현 후 수정 필요
            map.put("status", request.getStatus());

            // 상태가 2 (업체 매칭)인 경우, 견적서 정보 추가
            if (request.getStatus() == 2) {
                List<VendorRequestMapping> vendorRequestMappings = vendorRequestMappingRepository.findByRequest(request);

                // 견적서 정보를 리스트로 추가
                List<Map<String, Object>> vendorDetails = new ArrayList<>();
                for (VendorRequestMapping mapping : vendorRequestMappings) {
                    Map<String, Object> vendorMap = new HashMap<>();
                    vendorMap.put("vendor_name", mapping.getVendor().getVendorName());
                    vendorMap.put("estimate_price", mapping.getEstimatePrice());
                    vendorMap.put("estimate_time", mapping.getEstimateTime());
                    vendorMap.put("additional_comment", mapping.getAdditionalComment());

                    vendorDetails.add(vendorMap);
                }

                // 견적서 정보 포함
                map.put("vendor_details", vendorDetails);
            }

            response.add(map);
        }

        return response;
    }

    // 완료된 요청서 조회
    public List<ServiceRequest> doneServiceRequest(User user) {

        // [하우스유저매핑]과 연결
        HouseUserMapping houseUserMapping = houseUserMappingRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 매핑 정보를 찾을 수 없습니다."));

        // 해당 [하우스유저매핑]과 연결된 요청서 조회 & 조회된 요청서 중 "처리 완료"인 것 조회 및 반환
        List<ServiceRequest> doneRequests = serviceRequestRepository.findByHouseUserMappingAndStatus(houseUserMapping, 3);

        return doneRequests;
    }

    // 수리/공사 요청서 조회
    public Map<String, Object> getServiceRequest(Long requestId, User user) {

        // [하우스유저매핑]과 연결
        HouseUserMapping houseUserMapping = houseUserMappingRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 매핑 정보를 찾을 수 없습니다."));

        // 요청서 조회
        ServiceRequest serviceRequest = serviceRequestRepository.findByRequestIdAndHouseUserMapping(requestId, houseUserMapping);

        Map<String, Object> response = new HashMap<>();
        response.put("request_id", serviceRequest.getRequestId());
        response.put("category", serviceRequest.getCategory());
        response.put("created_at", serviceRequest.getCreatedAt().toString());
        response.put("address", serviceRequest.getHouseUserMapping().getHouse().getHouseAddress());

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> requestImages = new ArrayList<>(); // 기본값으로 빈 리스트 초기화

        try {
            requestImages = objectMapper.readValue(
                    serviceRequest.getRequestImage(),
                    new TypeReference<List<String>>() {}
            );
        } catch (JsonProcessingException e) {
            System.err.println("Failed to parse JSON: " + e.getMessage());
            throw new RuntimeException("Invalid request_image format", e);
        }
        response.put("request_image", requestImages);
        response.put("request_description", serviceRequest.getRequestDescription());
        response.put("status", serviceRequest.getStatus());

        // 스케줄 데이터 추가
        List<Map<String, Object>> schedules = serviceRequest.getConstructionDates().stream()
                .map(constructionDate -> {
                    Map<String, Object> schedule = new HashMap<>();
                    schedule.put("date", constructionDate.getDate().toString());
                    schedule.put("times", constructionDate.getTimes());
                    return schedule;
                })
                .toList();
        response.put("request_schedule", schedules);

        return response;
    }

    // 수리/공사 요청서 삭제
    public void deleteServiceRequest(Long requestId, User user) {

        // [하우스유저매핑]과 연결
        HouseUserMapping houseUserMapping = houseUserMappingRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 매핑 정보를 찾을 수 없습니다."));

        ServiceRequest serviceReqeuest = serviceRequestRepository.findByRequestIdAndHouseUserMapping(requestId, houseUserMapping);
        serviceRequestRepository.delete(serviceReqeuest);
    }
}
