package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseUserMappingRepository extends JpaRepository<HouseUserMapping, Long> {

    HouseUserMapping findById(String LoginId);
}
