package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
