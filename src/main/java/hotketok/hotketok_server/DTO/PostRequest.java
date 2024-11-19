package hotketok.hotketok_server.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostRequest {
    @JsonProperty("post_id") // 요청 Body의 post_id와 매핑
    private Long postId;
    private String title;
    private String content;
    private String tag;
    private String image;
    private Boolean anonymity;
}
