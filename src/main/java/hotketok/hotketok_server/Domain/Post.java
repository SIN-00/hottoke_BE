package hotketok.hotketok_server.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

//    @ManyToOne
//    @JoinColumn(name = "receiver_id", nullable = false)
//    private User receiver;
//
//    @ManyToOne
//    @JoinColumn(name = "sender_id", nullable = false)
//    private User sender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String tag;

    @Column(nullable = false)
    private Boolean anonymity;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}

