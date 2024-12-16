package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.CustomUserDetails;
import hotketok.hotketok_server.DTO.JoinDto;
import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
import hotketok.hotketok_server.Repository.UserRepository;
import hotketok.hotketok_server.Service.HouseUserMappingService;
import hotketok.hotketok_server.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final HouseUserMappingService houseUserMappingService;
    private final UserRepository userRepository;
    private final HouseUserMappingRepository houseUserMappingRepository;

    @PostMapping("/join")
    public String join(@RequestBody JoinDto joinDTO) {
        // 요청 데이터 로그
        System.out.println("Request received at /join: " + joinDTO);

        userService.joinProcess(joinDTO);
        String loginId = joinDTO.getLoginId();
        Long userId = userRepository.findByLoginId(loginId).getId();
        houseUserMappingService.houseUserSave(joinDTO, userId);


        return "회원가입에 성공하셨습니다!";
    }

    @GetMapping("/get-address") // 메인 페이지에서 유저별 주소 요청
    public ResponseEntity<Map<String, String>> getAddress(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String loginId = userDetails.getUser().getLoginId();
        User user = userRepository.findByLoginId(loginId);

        HouseUserMapping houseUserMapping = houseUserMappingRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 매핑 정보를 찾을 수 없습니다."));

        String address = houseUserMapping.getHouse().getHouseAddress();
        String unitNumber = houseUserMapping.getUnitNumber();

        // JSON 형태로 응답하기 위해 Map을 사용
        Map<String, String> response = new HashMap<>();
        response.put("address", address);
        response.put("unitNumber", unitNumber);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/existId")
    public String existId(@RequestParam("loginId") String loginId) {

        Boolean isExist = userRepository.existsByLoginId(loginId);
        if (isExist) {
            return "이미 존재하는 아이디입니다.";
        }
        else{
            return "성공";
        }
    }
}
