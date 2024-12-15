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
    public ResponseEntity<Map<String, String>> KnockPost(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody KnockKnockDTO knockKnockRequest){

        String loginId = userDetails.getUser().getLoginId();
        User sender = userRepository.findByLoginId(loginId);

        // Receiver 유효성 확인
        User receiver = userRepository.findById(knockKnockRequest.getReceiver().getId())
                .orElse(null);

        if (receiver == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid receiver ID"));
        }


        Optional<HouseUserMapping> userMappings = houseUserMappingRepository.findByUser(sender);
        if (userMappings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // KnockKnock 객체 생성
        KnockKnock knockKnock = KnockKnock.builder()
                .receiver(receiver)
                .sender(sender)
                .houseUserMapping(userMappings.get()) // Sender의 House 설정
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
