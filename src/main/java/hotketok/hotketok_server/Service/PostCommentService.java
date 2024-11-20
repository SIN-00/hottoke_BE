package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.DTO.PostCommentRequest;
import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Domain.Post;
import hotketok.hotketok_server.Domain.PostComment;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.PostCommentRepository;
import hotketok.hotketok_server.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    // 댓글 생성
    public void createComment(PostCommentRequest postCommentRequest) {
        User currentUser = userService.getCurrentUser();

        // 게시글 존재 여부 확인
        Post post = postRepository.findById(postCommentRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postCommentRequest.getPostId()));

        PostComment comment = new PostComment();
        comment.setContent(postCommentRequest.getContent());
        comment.setPost(post);
        comment.setUser(currentUser);
        comment.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        comment.setUpdatedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0));

        postCommentRepository.save(comment);
    }
}
