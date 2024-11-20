package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
