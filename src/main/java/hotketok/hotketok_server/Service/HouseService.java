package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseService {
    private final HouseRepository houseRepository;

    @Autowired
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public House getCurrentHouse() {  // 현재 mock 데이터 반환
        return houseRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Mock house not found")); // mock 데이터 없어진 경우 예외 처리
    }
}