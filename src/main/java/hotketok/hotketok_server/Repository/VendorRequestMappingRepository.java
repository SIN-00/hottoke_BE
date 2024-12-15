package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.ServiceRequest;
import hotketok.hotketok_server.Domain.VendorRequestMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRequestMappingRepository extends JpaRepository<VendorRequestMapping, Long> {

    List<VendorRequestMapping> findByRequest(ServiceRequest request);
}
