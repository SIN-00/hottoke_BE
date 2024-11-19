package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.PostRequest;
import hotketok.hotketok_server.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> createPost(@RequestBody PostRequest postRequest) {

        postService.createPost(postRequest);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    // 게시물 조회
    @GetMapping("/post")
    public ResponseEntity<Map<String, Map<String, Object>>> getPostsByHouse() {
        Map<String, Map<String, Object>> posts = postService.getPostsByHouse();
        return ResponseEntity.ok(posts);
    }

    // 게시물 수정
    @PatchMapping("/post")
    public ResponseEntity<Map<String, String>> updatePost(@RequestBody PostRequest postRequest) {
        boolean isUpdated = postService.updatePost(postRequest);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    // 게시물 삭제
    @DeleteMapping("/post")
    public ResponseEntity<Map<String, String>> deletePost(@RequestParam("post_id") Long postId) {
        boolean isDeleted = postService.deletePost(postId);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
