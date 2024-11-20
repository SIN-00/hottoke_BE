package hotketok.hotketok_server.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PostCommentRequest {
    private Long postId;
    private String content;
}
