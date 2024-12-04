package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.JoinDto;
import hotketok.hotketok_server.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public String join(@RequestBody JoinDto joinDTO) {
        // 요청 데이터 로그
        System.out.println("Request received at /join: " + joinDTO);

        userService.joinProcess(joinDTO);
        return "회원가입에 성공하셨습니다!";
    }
}
