package hotketok.hotketok_server.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import hotketok.hotketok_server.DTO.AddressResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class AddressService {
    @Value("${juso.api.url}")
    private String apiUrl;

    @Value("${juso.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public AddressService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AddressResponse searchAddress(String keyword) {
        try {
            String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            String url = String.format("%s?confmKey=%s&keyword=%s&resultType=json", apiUrl, apiKey, encodedKeyword);

            System.out.println("Request URL: " + url);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // JSON 응답 처리
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.getBody(), AddressResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("주소 검색 중 오류 발생", e);
        }
    }
}
