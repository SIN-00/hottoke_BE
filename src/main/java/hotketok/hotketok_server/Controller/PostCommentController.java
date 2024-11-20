package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.PostCommentRequest;
import hotketok.hotketok_server.Service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    // 게시물 댓글 작성
    @PostMapping("/post/comment")
    public ResponseEntity<Map<String, String>> createComment(@RequestBody PostCommentRequest commentRequest) {

        postCommentService.createComment(commentRequest);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    // 게시물 댓글 조회
    @GetMapping("/post/comment")
    public ResponseEntity<Map<String, Object>> getCommentsByPostId(@RequestParam("post_id") Long postId) {

        Map<String, Object> response = postCommentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(response);
    }

    // 게시물 댓글 수정
    @PatchMapping("/post/comment")
    public ResponseEntity<Map<String, String>> updateComment(@RequestBody Map<String, Object> requestBody) {

        Long postId = Long.valueOf(requestBody.get("post_id").toString());
        Long commentId = Long.valueOf((requestBody.get("comment_id").toString()));
        String content = requestBody.get("content").toString();

        boolean updateResult = postCommentService.updateComment(postId, commentId, content);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    // 게시물 댓글 삭제
    @DeleteMapping("/post/comment")
    public ResponseEntity<Map<String, String>> deleteComment(@RequestBody Map<String, Object> requestBody) {

        Long postId = Long.valueOf(requestBody.get("post_id").toString());
        Long commentId = Long.valueOf(requestBody.get("comment_id").toString());

        boolean deleteResult = postCommentService.deleteComment(postId, commentId);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
