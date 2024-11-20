package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
}
