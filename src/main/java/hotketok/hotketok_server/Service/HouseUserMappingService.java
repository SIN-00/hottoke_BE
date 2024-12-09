//package hotketok.hotketok_server.Service;
//
//import hotketok.hotketok_server.Domain.HouseUserMapping;
//import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class HouseUserMappingService {
//
//    private final HouseUserMappingRepository houseUserMappingRepository;
//
//    @Autowired
//    public HouseUserMappingService(HouseUserMappingRepository houseUserMappingRepository) {
//        this.houseUserMappingRepository = houseUserMappingRepository;
//    }
//
//    public HouseUserMapping getCurrentHouseUser() {  // 현재 Mock 정보 반환
//        return houseUserMappingRepository.findByHouse_HouseIdAndUser_UserId(1L, 1L)
//                .orElseThrow(() -> new RuntimeException("Mock HouseUser not found"));
//    }
//}
