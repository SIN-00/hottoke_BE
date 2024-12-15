package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    List<ServiceRequest> findByHouseUserMapping(HouseUserMapping houseUserMapping);

    // 진행 및 완료된 요청서 조회
    List<ServiceRequest> findByHouseUserMappingAndStatus(HouseUserMapping houseUserMapping, Integer requestStatus);

    // 수리/공사 요청서 조회
    ServiceRequest findByRequestIdAndHouseUserMapping(Long requestId, HouseUserMapping houseUserMapping);

    // 완료되지 않은 요청서 조회
    List<ServiceRequest> findByStatus(Integer status);

    ServiceRequest findByRequestId(Long requestId);
}
