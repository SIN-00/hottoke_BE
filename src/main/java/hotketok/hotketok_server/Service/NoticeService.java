package hotketok.hotketok_server.Service;


import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.Notice;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
import hotketok.hotketok_server.Repository.NoticeRepository;
import hotketok.hotketok_server.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final HouseUserMappingRepository houseUserMappingRepository;
    private final UserRepository userRepository;

    public Map<String, Map<String, Object>> getUserNotices(User user) {

        // 영속화 상태 확인을 위해 먼저 DB에서 user 조회
        User persistentUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 유저 ID입니다: " + user.getId()));

        HouseUserMapping houseUserMapping = houseUserMappingRepository.findByUser(persistentUser)
                .orElseThrow(() -> new IllegalArgumentException("유저에 해당하는 매핑 정보를 찾을 수 없습니다."));

        List<Notice> notices = noticeRepository.findByHouseUserMapping(houseUserMapping);

        Map<String, Map<String, Object>> response = new HashMap<>();
        for (Notice notice : notices) {
            Map<String, Object> noticeDetails = new HashMap<>();
            noticeDetails.put("author", notice.getHouseUserMapping().getUser().getUsername());
            noticeDetails.put("content", notice.getContent());
            noticeDetails.put("createdAt", notice.getCreatedAt().toString());
            response.put(String.valueOf(notice.getNoticeId()), noticeDetails);
        }
        return response;
    }
}