package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HouseUserMappingRepository extends JpaRepository<HouseUserMapping, Long> {

    Optional<HouseUserMapping> findByHouseUserMappingId(Long houseUserMappingId);

    Optional<HouseUserMapping> findByUser(User user);

    List<HouseUserMapping> findAllByHouse(House house);

    @Query("SELECT hum.user FROM HouseUserMapping hum WHERE hum.unitNumber = :unitNumber")
    Optional<User> findUserByUnitNumber(@Param("unitNumber") String unitNumber);


}
