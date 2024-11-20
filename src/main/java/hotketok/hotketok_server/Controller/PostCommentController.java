package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.PostCommentRequest;
import hotketok.hotketok_server.Service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
