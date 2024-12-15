package hotketok.hotketok_server.Repository;

import hotketok.hotketok_server.Domain.KnockKnock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnockKnockRepository extends JpaRepository<KnockKnock, Integer> {
}
