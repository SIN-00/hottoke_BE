package hotketok.hotketok_server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private String title;
    private String content;
    private String tag;
    private String image;
    private boolean anonymity;
}

