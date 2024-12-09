package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.HouseRepository;
import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
import hotketok.hotketok_server.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class HouseService {

    @Value("${juso.api.url}") // API URL
    private String apiUrl;

    @Value("${juso.api.key}") // API Key
    private String apiKey;

    private final RestTemplate restTemplate;
    private final HouseUserMappingRepository houseUserMappingRepository;
    private final HouseRepository houseRepository;

    public String getRawJsonResponse(String keyword) {
        try {
            // 키워드 인코딩
            //String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            String url = String.format("%s?confmKey=%s&keyword=%s&resultType=json", apiUrl, apiKey, keyword);

            // HTTP 요청 및 응답 받기
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // JSON 문자열 반환
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("주소 검색 중 오류 발생", e);
        }
    }

    // 사용자 주소 선택
    public String selectAddress(String address, String unit_number, User user) {

        // 유저의 [하우스유저매핑] 레코드 조회
        HouseUserMapping houseUserMapping = houseUserMappingRepository.findById(user.getLoginId());

        // [하우스유저매핑] 테이블에 있는 [주택] 연결
        House house = houseRepository.findByHouseId(houseUserMapping.getHouse_id());

        // 주소 정보 저장
        // 1. [하우스유저매핑] 테이블에 해당 유저와 세대 번호 등록
        houseUserMapping.setUser_id(user);
        houseUserMapping.setUnit_number(unit_number);

        // 2. [주택] 테이블에 주소 등록
        house.setHouse_address(address);

        houseUserMappingRepository.save(houseUserMapping);
        houseRepository.save(house);

        return "success";
    }
}
