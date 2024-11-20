package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.DTO.AddressResponse;
import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseService {
    private final HouseRepository houseRepository;
    private final AddressService addressService;

    @Autowired
    public HouseService(HouseRepository houseRepository, AddressService addressService) {
        this.houseRepository = houseRepository;
        this.addressService = addressService;
    }

    public House getCurrentHouse() {
        return houseRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Mock house not found"));
    }

    // 주소 저장 (예외 처리 추가)
    public House saveAddress(String inputAddress) {
        AddressResponse response = addressService.searchAddress(inputAddress);

        if (response == null) {
            System.out.println("API 응답이 null입니다.");
            throw new IllegalArgumentException("주소 API 호출 실패");
        }

        if (response.getResults() == null) {
            System.out.println("API 응답에 results 필드가 없습니다.");
            throw new IllegalArgumentException("주소 검색 결과가 없습니다.");
        }

        String fullAddress = response.getResults().getJibunAddr();
        if (fullAddress == null) {
            System.out.println("jibunAddr 필드가 null입니다.");
            throw new IllegalArgumentException("주소를 찾을 수 없습니다.");
        }

        // House 객체 생성 및 저장
        House house = new House();
        house.setHouseAddress(fullAddress);
        return houseRepository.save(house);
    }

}
