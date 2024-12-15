package hotketok.hotketok_server.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddressResponse {
    private Results results;

    @Getter
    @Setter
    public static class Results {
        private List<Result> juso; // JSON의 "juso" 배열 매핑
    }

    @Getter
    @Setter
    public static class Result {
        private String roadAddr;  // 도로명 주소
        private String jibunAddr; // 지번 주소
        private String common;    // 기타 정보
    }
}

