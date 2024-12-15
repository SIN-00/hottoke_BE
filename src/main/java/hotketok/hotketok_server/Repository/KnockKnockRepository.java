package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.KnockKnock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KnockKnockRepository extends JpaRepository<KnockKnock, Integer> {

    List<KnockKnock> findAllBySenderIdOrderByCreatedAtDesc(Long senderId);
}
