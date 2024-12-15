package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 특정 House와 관련된 공지사항 조회
    List<Notice> findByHouseUserMapping(HouseUserMapping houseUserMapping);