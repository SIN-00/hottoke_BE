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
    public void createPost(PostRequest postRequest) {
        House currentHouse = houseService.getCurrentHouse();
        User currentUser = userService.getCurrentUser();

        Post post = Post.builder()
                .title(postRequest.getTitle() != null ? postRequest.getTitle() : "기본 제목")
                .content(postRequest.getContent() != null ? postRequest.getContent() : "기본 내용")
                .tag(postRequest.getTag() != null ? postRequest.getTag() : "기본 태그")
                .image(postRequest.getImage()) // null 허용
                .anonymity(postRequest.getAnonymity() != null ? postRequest.getAnonymity() : false)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .updatedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                .house(currentHouse)
                .user(currentUser)
                .build();

        postRepository.save(post);
    }

    // 게시물 조회
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

    // 게시물 수정
    public boolean updatePost(PostRequest postRequest) {

        // post_id를 기준으로 정보 찾음
        Optional<Post> optionalPost = postRepository.findById(postRequest.getPostId());
        if (optionalPost.isEmpty()) {
            return false; // 게시글이 없으면 수정 불가
        }

        Post post = optionalPost.get();

        // request에 있는 것만 수정
        if (postRequest.getTitle() != null) {
            post.setTitle(postRequest.getTitle());
        }
        if (postRequest.getContent() != null) {
            post.setContent(postRequest.getContent());
        }
        if (postRequest.getTag() != null) {
            post.setTag(postRequest.getTag());
        }
        if (postRequest.getImage() != null) {
            post.setImage(postRequest.getImage());
        }
        if (postRequest.getAnonymity() != null) {
            post.setAnonymity(postRequest.getAnonymity());
        }

        // updated_at 설정
        post.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));

        postRepository.save(post);
        return true;
    }

    // 게시물 삭제
    public boolean deletePost(Long postId)  {
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
