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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final HouseService houseService;
    private final UserService userService;

    // 게시물 생성
    public void createPost(PostRequest postRequest, User user) {
        House currentHouse = houseService.getCurrentHouse();
        User currentUser = userService.getCurrentUser();

        Post post = Post.builder()
                .content(postRequest.getContent() != null ? postRequest.getContent() : "기본 내용")
                .tag(postRequest.getTag() != null ? postRequest.getTag() : "기본 태그")
                .anonymity(postRequest.getAnonymity() != null ? postRequest.getAnonymity() : false)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .house(currentHouse)
                .build();

        postRepository.save(post);
    }

    // 게시물 조회
    public Map<String, Map<String, Object>> getPostsByHouse(User user) {
        House currentHouse = houseService.getCurrentHouse();

        return postRepository.findByHouse(currentHouse).stream()
                .collect(Collectors.toMap(
                        post -> String.valueOf(post.getPostId()), // Key: postId
                        post -> { // Value: 게시글 데이터를 Map으로 변환
                            Map<String, Object> result = new HashMap<>();
                            result.put("content", post.getContent());
                            result.put("tag", post.getTag());
                            result.put("anonymity", post.getAnonymity());
                            result.put("created_at", post.getCreatedAt());
                            return result;
                        }
                ));
    }

    // 게시물 수정
    public boolean updatePost(PostRequest postRequest, User user) {

        // post_id를 기준으로 정보 찾음
        Optional<Post> optionalPost = postRepository.findById(postRequest.getPostId());
        if (optionalPost.isEmpty()) {
            return false; // 게시글이 없으면 수정 불가
        }

        Post post = optionalPost.get();

        // request에 있는 것만 수정
        if (postRequest.getContent() != null) {
            post.setContent(postRequest.getContent());
        }
        if (postRequest.getTag() != null) {
            post.setTag(postRequest.getTag());
        }
        if (postRequest.getAnonymity() != null) {
            post.setAnonymity(postRequest.getAnonymity());
        }

        postRepository.save(post);
        return true;
    }

    // 게시물 삭제
    public boolean deletePost(Long postId, User user)  {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            return true;
        }
        return false; // 게시글이 존재하지 않는 경우
    }

    // 오늘 날짜 설정
    private LocalDate getCurrentDate() {
        return LocalDate.now(ZoneId.of("Asia/Seoul"));
    }
}
