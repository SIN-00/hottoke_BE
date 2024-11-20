package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.HouseUserMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseUserMappingRepository extends JpaRepository<HouseUserMapping, Long> {

    // 하우스 id와 유저 id로 매핑
    Optional<HouseUserMapping> findByHouse_HouseIdAndUser_UserId(Long houseId, Long userId);
}
