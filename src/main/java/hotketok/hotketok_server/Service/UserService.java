package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.DTO.JoinDto;
import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Exception.UsernameAlreadyExistsException;
import hotketok.hotketok_server.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void joinProcess(JoinDto joinDTO) {
        String loginId = joinDTO.getLoginId();

        // 아이디 중복 체크
        if (userRepository.existsByLoginId(loginId)) {
            throw new UsernameAlreadyExistsException("이미 존재하는 아이디입니다.");

        }

        User userData = new User();
        userData.setUsername(joinDTO.getUsername());
        userData.setLoginId(joinDTO.getLoginId());
        userData.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        userData.setRole("ROLE_USER");

        userRepository.save(userData);
        System.out.println("유저 추가 완료");
    }
}
