package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.DTO.CustomUserDetails;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        //DB에서 조회
        User userData = userRepository.findByLoginId(loginId);

        if (userData != null) {
            System.out.println("Password from DB: " + userData.getPassword());
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(userData);
        }

        return null;
    }
}