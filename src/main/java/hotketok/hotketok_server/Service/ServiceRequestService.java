package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.ServiceRequest;
import hotketok.hotketok_server.Repository.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

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


}
