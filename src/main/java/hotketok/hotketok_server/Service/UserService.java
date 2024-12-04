package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.DTO.JoinDto;
import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Domain.User;
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


        User userData = new User();
        userData.setUsername(joinDTO.getUsername());
        userData.setLoginId(joinDTO.getLoginId());
        userData.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        userData.setRole("ROLE_USER");
        //userData.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));

        userRepository.save(userData);
        System.out.println("유저 추가 완료");
    }
}
