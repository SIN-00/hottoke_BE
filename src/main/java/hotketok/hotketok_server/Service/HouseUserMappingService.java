package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.DTO.JoinDto;
import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.HouseRepository;
import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
import hotketok.hotketok_server.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HouseUserMappingService {
    private final HouseUserMappingRepository houseUserMappingRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;

    public void houseUserSave(JoinDto joinDto, Long userId) {
        // JoinDto에서 address 가져오기
        String address = joinDto.getAddress();
        String unitNumber = joinDto.getUnitNumber();

        House house = houseRepository.findByHouseAddress(address)
                .orElseGet(() -> {
                    // 주소에 해당하는 House가 없으면 새로 생성
                    House newHouse = House.builder()
                            .houseAddress(address)
                            .unitCode("default") // 기본값
                            .monthlyRent(0.0)    // 기본값
                            .maintenanceCost(0.0) // 기본값
                            .build();
                    return houseRepository.save(newHouse); // 저장 후 반환
                });

        // User를 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 유저 ID입니다."));

        // HouseUserMapping 생성
        HouseUserMapping houseUserMapping = HouseUserMapping.builder()
                .house(house)
                .user(user)
                .unitNumber(unitNumber) // 기본값 설정, 필요 시 JoinDto에서 가져오기
                .build();

        // 저장
        houseUserMappingRepository.save(houseUserMapping);
    }

}
