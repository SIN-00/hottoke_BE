package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseRepository extends JpaRepository <House, Long>{

    House findByHouseId(House id);
}
