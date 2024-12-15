package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.Domain.KnockKnock;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.KnockKnockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class KnockKnockService {
    private final KnockKnockRepository knockKnockRepository;

    public List<KnockKnock> getKnocksBySender(Long senderId) {
        // 특정 Sender의 모든 KnockKnock 데이터를 반환
        return knockKnockRepository.findAllBySenderIdOrderByCreatedAtDesc(senderId);
    }

    public List<KnockKnock> getKnocksByReciever(Long recieverId) {
        // 특정 Sender의 모든 KnockKnock 데이터를 반환
        return knockKnockRepository.findAllBySenderIdOrderByCreatedAtDesc(recieverId);
    }
}
