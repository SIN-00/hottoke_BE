package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.CustomUserDetails;
import hotketok.hotketok_server.DTO.KnockKnockDTO;
import hotketok.hotketok_server.Domain.HouseUserMapping;
import hotketok.hotketok_server.Domain.KnockKnock;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.HouseUserMappingRepository;
import hotketok.hotketok_server.Repository.KnockKnockRepository;
import hotketok.hotketok_server.Repository.UserRepository;
import hotketok.hotketok_server.Service.KnockKnockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class KnockKnockController {

    private final UserRepository userRepository;
    private final KnockKnockRepository knockKnockRepository;
    private final HouseUserMappingRepository houseUserMappingRepository;
    private final KnockKnockService knockKnockService;

    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> knockPost(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody KnockKnockDTO knockKnockRequest) {
        // Sender 정보 가져오기
        String loginId = userDetails.getUser().getLoginId();
        User sender = userRepository.findByLoginId(loginId);

        if (sender == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Sender not found"));
        }

        // Receiver 유효성 확인 (unitNumber를 통해 찾기)
        String unitNumber = knockKnockRequest.getUnitNumber();
        Optional<User> receiverOptional = houseUserMappingRepository.findUserByUnitNumber(unitNumber);

        if (receiverOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid unit number: receiver not found"));
        }
        User receiver = receiverOptional.get();

        // Sender의 HouseUserMapping 확인
        Optional<HouseUserMapping> userMappingOptional = houseUserMappingRepository.findByUser(sender);
        if (userMappingOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "HouseUserMapping not found for sender"));
        }
        HouseUserMapping houseUserMapping = userMappingOptional.get();

        // KnockKnock 객체 생성
        KnockKnock knockKnock = KnockKnock.builder()
                .receiverId(receiver.getId())
                .senderId(sender.getId())
                .houseUserMapping(houseUserMapping)
                .content(knockKnockRequest.getContent())
                .tag(knockKnockRequest.getTag())
                .anonymity(knockKnockRequest.getAnonymity())
                .createdAt(knockKnockRequest.getCreatedAt())
                .timeArray(knockKnockRequest.getTimeArray())
                .build();

        // KnockKnock 저장
        knockKnockRepository.save(knockKnock);

        // 성공 응답 반환
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "KnockKnock created successfully");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/post-send")
    public ResponseEntity<Map<Long, Map<String, Object>>> getKnockList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String loginId = userDetails.getUser().getLoginId();
        User sender = userRepository.findByLoginId(loginId);
        Long senderId = sender.getId();

        System.out.println("post-send senderId "+senderId);

        // Service에서 최신 날짜순으로 정렬된 KnockKnock 리스트 가져오기
        List<KnockKnock> knocks = knockKnockService.getKnocksBySender(senderId);

        // JSON 형태로 구성
        Map<Long, Map<String, Object>> response = knocks.stream().collect(Collectors.toMap(
                KnockKnock::getKnockId,
                knock -> Map.of(
                        "receiverId", knock.getReceiverId(),
                        "content", knock.getContent(),
                        "tag", knock.getTag(),
                        "anonymity", knock.getAnonymity(),
                        "createdAt", knock.getCreatedAt(),
                        "timeArray", knock.getTimeArray()
                )
        ));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/post-recieve")
    public ResponseEntity<Map<Long, Map<String, Object>>> getKnockRecieveList(@AuthenticationPrincipal CustomUserDetails userDetails) {


        String loginId = userDetails.getUser().getLoginId();
        System.out.println("Input loginId: " + loginId);
        User receiver = userRepository.findByLoginId(loginId);
        System.out.println("Receiver: " + receiver);
        Long receiverId = receiver.getId();

        // Service에서 최신 날짜순으로 정렬된 KnockKnock 리스트 가져오기
        List<KnockKnock> knocks = knockKnockService.getKnocksByReciever(receiverId);

        // JSON 형태로 구성
        Map<Long, Map<String, Object>> response = knocks.stream().collect(Collectors.toMap(
                KnockKnock::getKnockId,
                knock -> Map.of(
                        "senderId", knock.getSenderId(),
                        "content", knock.getContent(),
                        "tag", knock.getTag(),
                        "anonymity", knock.getAnonymity(),
                        "createdAt", knock.getCreatedAt(),
                        "timeArray", knock.getTimeArray()
                )
        ));

        return ResponseEntity.ok(response);
    }
}
