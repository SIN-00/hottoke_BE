package hotketok.hotketok_server.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AddressResponse {
    private Result results;

    @Getter
    @Setter
    public static class Result {
        private String jibunAddr; // 지번 주소
        private String roadAddr; // 도로명 주소
    }
}
