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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final HouseService houseService;
    private final UserService userService;

    public void createPost(PostRequest postRequest) {
        House currentHouse = houseService.getCurrentHouse();
        User currentUser = userService.getCurrentUser();

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .tag(postRequest.getTag())
                .image(postRequest.getImage())
                .anonymity(postRequest.isAnonymity())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .updatedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                .house(currentHouse)
                .user(currentUser)
                .build();

        postRepository.save(post);
    }

    public Map<String, Map<String, Object>> getPostsByHouse() {
        House currentHouse = houseService.getCurrentHouse();

        return postRepository.findByHouse(currentHouse).stream()
                .collect(Collectors.toMap(
                        post -> String.valueOf(post.getPostId()), // Key: postId
                        post -> { // Value: 게시글 데이터를 Map으로 변환
                            Map<String, Object> result = new HashMap<>();
                            result.put("tenant_id", post.getUser().getUserId());
                            result.put("title", post.getTitle());
                            result.put("content", post.getContent());
                            result.put("tag", post.getTag());
                            result.put("image", post.getImage());
                            result.put("anonymity", post.getAnonymity());
                            result.put("created_at", post.getCreatedAt());
                            result.put("updated_at", post.getUpdatedAt());
                            result.put("like_count", post.getLikeCount());
                            result.put("comment_count", post.getComments() != null ? post.getComments().size() : 0);
                            return result;
                        }
                ));
    }

    // 오늘 날짜 설정
    private LocalDate getCurrentDate() {
        return LocalDate.now(ZoneId.of("Asia/Seoul"));
    }
}
