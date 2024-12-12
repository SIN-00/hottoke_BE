package hotketok.hotketok_server.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDto {

    private String username;

    private String loginId;

    private String password;

    private String address;

    private String unitNumber;


    //    @Min(value = 0, message = "올바른 나이를 입력해주세요.")
//    @Max(value = 120, message = "올바른 나이를 입력해주세요")

    //    @Pattern(regexp = "남|여", message = "성별은 '남,여'로 입력해주세요.")

}

