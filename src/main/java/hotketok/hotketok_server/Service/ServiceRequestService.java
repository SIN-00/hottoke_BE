package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.ServiceRequest;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
import hotketok.hotketok_server.Repository.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {

    private final HouseUserMappingRepository houseUserMappingRepository;
    private final ServiceRequestRepository serviceRequestRepository;

    // 진행중인 요청서 조회
    public List<ServiceRequest> progressServiceRequest(User user) {

        // [하우스유저매핑]과 연결
        HouseUserMapping houseUserMapping = houseUserMappingRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 매핑 정보를 찾을 수 없습니다."));

        // 해당 [하우스유저매핑]과 연결된 요청서 조회 & 조회된 요청서 중 "업체 찾는 중"인 것 조회 및 반환
        List<ServiceRequest> progressRequests = serviceRequestRepository.findByHouseUserMappingAndStatus(houseUserMapping, 0);

        return progressRequests;
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
    public Map<String, Object> getServiceRequest(Long requestId) {

        // 요청서 조회
        ServiceRequest serviceRequest = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("해당 요청서를 찾을 수 없습니다."));

        Map<String, Object> response = new HashMap<>();
        response.put("request_id", serviceRequest.getRequestId());
        response.put("category", serviceRequest.getCategory());
        response.put("created_at", serviceRequest.getCreatedAt().toString());
        response.put("address", serviceRequest.getHouseUserMapping().getHouse().getHouseAddress());
        response.put("request_image", serviceRequest.getRequestImage());
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

}
