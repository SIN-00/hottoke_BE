package hotketok.hotketok_server.Domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "houseUserMapping_id", nullable = false)
    private HouseUserMapping houseUserMapping;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now(); // 자동 생성 시간 설정
    }


}
