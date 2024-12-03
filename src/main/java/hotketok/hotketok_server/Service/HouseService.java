package hotketok.hotketok_server.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class HouseService {

    @Value("${juso.api.url}") // API URL
    private String apiUrl;

    @Value("${juso.api.key}") // API Key
    private String apiKey;

    private final RestTemplate restTemplate;

    public HouseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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
}
