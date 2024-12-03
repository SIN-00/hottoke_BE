package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.CustomUserDetails;
import hotketok.hotketok_server.DTO.PostRequest;
import hotketok.hotketok_server.Domain.User;
import hotketok.hotketok_server.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 작성
//    @PostMapping("/post")
//    public ResponseEntity<Map<String, String>> createPost(@RequestBody PostRequest postRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
//
//        Long userId = userDetails.getUser().getUserId();
//        postService.createPost(postRequest, userId);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("status", "success");
//        return ResponseEntity.ok(response);
//    }

    // 게시물 조회
    @GetMapping("/post")
    public ResponseEntity<Map<String, Map<String, Object>>> getPostsByHouse(@AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUser().getUserId();
        Map<String, Map<String, Object>> posts = postService.getPostsByHouse(userId);
        return ResponseEntity.ok(posts);
    }

    // 게시물 수정
    @PatchMapping("/post")
    public ResponseEntity<Map<String, String>> updatePost(@RequestBody PostRequest postRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUser().getUserId();
        boolean isUpdated = postService.updatePost(postRequest, userId);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    // 게시물 삭제
    @DeleteMapping("/post")
    public ResponseEntity<Map<String, String>> deletePost(@RequestParam("post_id") Long postId, @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUser().getUserId();
        boolean isDeleted = postService.deletePost(postId, userId);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
