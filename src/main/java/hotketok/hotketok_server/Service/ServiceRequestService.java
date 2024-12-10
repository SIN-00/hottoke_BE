package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.ServiceRequest;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
import hotketok.hotketok_server.Repository.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
