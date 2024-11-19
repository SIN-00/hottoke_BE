package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {  // 현재 mock 데이터 반환
        return userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Mock user not found")); // mock 데이터 없어진 경우 예외 처리
    }
}
