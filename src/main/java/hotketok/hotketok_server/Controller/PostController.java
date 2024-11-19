package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.PostRequest;
import hotketok.hotketok_server.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
}
