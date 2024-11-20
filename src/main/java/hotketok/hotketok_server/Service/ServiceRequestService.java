package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.ServiceRequest;
import hotketok.hotketok_server.Repository.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final HouseUserMappingService houseUserMappingService;

    // 요청서 작성
    public void createRequest(ServiceRequest serviceRequest) {

        HouseUserMapping currentHouseUser = houseUserMappingService.getCurrentHouseUser();

        ServiceRequest request = ServiceRequest.builder()
                .houseUserMapping(currentHouseUser)
                .requestImage(serviceRequest.getRequestImage() != null ? serviceRequest.getRequestImage(): "사진 없음") // 추후 수정 필요
                .requestDescription(serviceRequest.getRequestDescription())
                .constructionDate(serviceRequest.getConstructionDate())
                .parking(serviceRequest.getParking())
                .addRequest(serviceRequest.getAddRequest())
                .requestStatus(serviceRequest.getRequestStatus() != null ? serviceRequest.getRequestStatus(): "업체 찾는 중") // 초기 상태 설정
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        serviceRequestRepository.save(request);
    }

    // 요청서 조회
    public Map<String, Map<String, Object>> getRequests() {

        HouseUserMapping currentHouseUser = houseUserMappingService.getCurrentHouseUser();

        return serviceRequestRepository.findByHouseUserMapping(currentHouseUser).stream()
                .collect(Collectors.toMap(
                        serviceRequest -> String.valueOf(serviceRequest.getRequestId()),
                        serviceRequest -> { // Value: 요청서 데이터를 Map으로 변환
                            Map<String, Object> result = new HashMap<>();
                            result.put("request_schedule", serviceRequest.getConstructionDate());
                            result.put("request_image", serviceRequest.getRequestImage());
                            result.put("description", serviceRequest.getRequestDescription());
                            result.put("additional_request", serviceRequest.getAddRequest());
                            result.put("status", serviceRequest.getRequestStatus());
                            result.put("created_at", serviceRequest.getCreatedAt());
                            result.put("parking", serviceRequest.getParking());
                            return result;

                        }
                ));
    }

}
