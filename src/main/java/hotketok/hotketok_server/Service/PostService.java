package hotketok.hotketok_server.Service;

import hotketok.hotketok_server.DTO.PostRequest;
import hotketok.hotketok_server.Domain.Post;
import hotketok.hotketok_server.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void createPost(PostRequest postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .tag(postRequest.getTag())
                .image(postRequest.getImage())
                .anonymity(postRequest.isAnonymity())
                .build();

        postRepository.save(post);
    }
}
