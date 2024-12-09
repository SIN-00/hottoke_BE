package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByemail(String email);
    User findByemail(String email);
    Optional<User> findById(Long id);
    User findByLoginId(String loginId);

}
