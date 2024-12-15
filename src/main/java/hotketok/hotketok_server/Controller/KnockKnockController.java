package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.CustomUserDetails;
import hotketok.hotketok_server.DTO.KnockKnockDTO;
import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.KnockKnock;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
import hotketok.hotketok_server.Repository.KnockKnockRepository;
import hotketok.hotketok_server.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class KnockKnockController {

    private final UserRepository userRepository;
    private final KnockKnockRepository knockKnockRepository;
    private final HouseUserMappingRepository houseUserMappingRepository;

    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> knockPost(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody KnockKnock knockKnockRequest) {
        String loginId = userDetails.getUser().getLoginId();
        User sender = userRepository.findByLoginId(loginId);

        System.out.println("senderId"+sender.getId());
        System.out.println("receiverId"+knockKnockRequest.getReceiverId());
        // Receiver 유효성 확인
        User receiver = userRepository.findById(knockKnockRequest.getReceiverId())
                .orElse(null);

        if (receiver == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid receiver ID"));
        }

        Optional<HouseUserMapping> userMapping = houseUserMappingRepository.findByUser(sender);
        if (userMapping.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // KnockKnock 객체 생성
        KnockKnock knockKnock = KnockKnock.builder()
                .receiverId(receiver.getId())
                .senderId(sender.getId())
                .houseUserMapping(userMapping.get()) // Sender의 House 설정
                .content(knockKnockRequest.getContent())
                .tag(knockKnockRequest.getTag())
                .anonymity(knockKnockRequest.getAnonymity())
                .createdAt(knockKnockRequest.getCreatedAt())
                .timeArray(knockKnockRequest.getTimeArray())
                .build();

        // KnockKnock 저장
        knockKnockRepository.save(knockKnock);

        // 응답 반환
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

}
