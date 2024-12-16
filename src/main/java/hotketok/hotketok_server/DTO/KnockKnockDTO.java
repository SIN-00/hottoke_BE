package hotketok.hotketok_server.DTO;

import hotketok.hotketok_server.Domain.User;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
public class KnockKnockDTO {
    private String unitNumber; // Receiver의 ID만 받음
    private String content;
    private String tag;
    private Boolean anonymity;
    private LocalDateTime createdAt;
    private List<Boolean> timeArray;
}