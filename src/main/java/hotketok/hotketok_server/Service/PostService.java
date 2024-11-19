package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.DTO.PostRequest;
import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Domain.Post;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.HouseRepository;
import hotketok.hotketok_server.Repository.PostRepository;
import hotketok.hotketok_server.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final HouseService houseService;
    private final UserService userService;

    public void createPost(PostRequest postRequest) {
        House house = houseService.getCurrentHouse();
        User currentUser = userService.getCurrentUser();

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .tag(postRequest.getTag())
                .image(postRequest.getImage())
                .anonymity(postRequest.isAnonymity())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .updatedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                .house(house)
                .user(currentUser)
                .build();

        postRepository.save(post);
    }

    // 오늘 날짜 설정
    private LocalDate getCurrentDate() {
        return LocalDate.now(ZoneId.of("Asia/Seoul"));
    }
}
