package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByemail(String email);
    User findByEmail(String email);
    Optional<User> findById(Long id);
    User findByLoginId(String loginId);
    Boolean existsByLoginId(String loginId);
    Long findIdByUser(User user);

}
