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
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public String selectAddress(String address, String unitNumber, User user) {

        System.out.println("User: " + user);
        // 1. 유저의 [하우스유저매핑] 레코드 조회
        HouseUserMapping houseUserMapping = houseUserMappingRepository
                .findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 매핑 정보가 없습니다."));

        // 2. [하우스유저매핑] 테이블의 [주택] 연결
        House house = houseUserMapping.getHouse();

        // 3. [하우스유저매핑] 테이블에 해당 유저와 세대 번호 등록
        houseUserMapping.setUnitNumber(unitNumber);

        // 4. [주택] 테이블에 주소 등록
        house.setHouseAddress(address);

        houseUserMappingRepository.save(houseUserMapping);

        return "success";

        // 하우스를 등록하면 해당 유저와 주택을 연결하는 하우스유저매핑 테이블을 생성하는 로직 구현 필요
    }

}
