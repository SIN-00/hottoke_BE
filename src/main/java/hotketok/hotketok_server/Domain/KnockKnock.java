package hotketok.hotketok_server.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class KnockKnock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long knockId;

    @JoinColumn(name = "receiver_id", nullable = false)
    private Long receiverId;

    @JoinColumn(name = "sender_id", nullable = false)
    private Long senderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "houseUserMapping_id", nullable = false)
    private HouseUserMapping houseUserMapping;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String tag;

    @Column(nullable = false)
    private Boolean anonymity;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ElementCollection
    @CollectionTable(name = "knock_time_array", joinColumns = @JoinColumn(name = "knock_id"))
    @Column(name = "time", nullable = false)
    private List<Boolean> timeArray;
}

