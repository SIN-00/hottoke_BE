package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.DTO.PostCommentRequest;
import hotketok.hotketok_server.Domain.Post;
import hotketok.hotketok_server.Domain.PostComment;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Repository.PostCommentRepository;
import hotketok.hotketok_server.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    // 댓글 조회
    public Map<String, Object> getCommentsByPostId(Long postId) {
        List<PostComment> comments = postCommentRepository.findByPost_PostId(postId);
        User currentUser = userService.getCurrentUser();

        // 댓글 리스트 형식 변환 (response)
        List<Map<String, Object>> commentList = comments.stream()
                .map(comment -> {
                    Map<String, Object> commentMap = new HashMap<>();
                    commentMap.put("comment_id", comment.getCommentId());
                    commentMap.put("post_id", comment.getPost().getPostId());
                    commentMap.put("tenant_id", comment.getUser().getUserId());
                    commentMap.put("content", comment.getContent());
                    commentMap.put("created_at", comment.getCreatedAt());
                    commentMap.put("updated_at", comment.getUpdatedAt());
                    return commentMap;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("post_id", postId);
        response.put("comments", commentList);

        return response;
    }

    // 댓글 수정
    public boolean updateComment(Long postId, Long commentId, String content) {

        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("Post not found with id");
        }

        PostComment comment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id"));

        // 댓글과 게시글의 연관성 확인
        if (!comment.getPost().getPostId().equals(postId)) {
            throw new IllegalArgumentException("Comment does not belong to post");
        }

        // 댓글 업데이트
        comment.setContent(content);
        postCommentRepository.save(comment);

        return true;
    }

    // 댓글 삭제
    public boolean deleteComment(Long postId, Long commentId) {

        if(!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("Post not found with Id");
        }

        PostComment comment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with Id"));


        // 댓글과 게시글의 연관성 확인
        if (!comment.getPost().getPostId().equals(postId)) {
            throw new IllegalArgumentException("Comment does not belong to post");
        }

        // 댓글 삭제
        postCommentRepository.delete(comment);

        return true;
    }
}
