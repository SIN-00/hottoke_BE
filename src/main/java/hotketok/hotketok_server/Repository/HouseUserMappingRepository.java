package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HouseUserMappingRepository extends JpaRepository<HouseUserMapping, Long> {

    Optional<HouseUserMapping> findByHouseUserMappingId(Long houseUserMappingId);

    Optional<HouseUserMapping> findByUser(User user);

    List<HouseUserMapping> findAllByHouse(House house);

    Optional<User> findUserByUnitNumber(String unitNumber);

}
