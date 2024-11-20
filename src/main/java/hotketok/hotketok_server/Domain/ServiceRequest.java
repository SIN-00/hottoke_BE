package hotketok.hotketok_server.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @Column
    private String requestImage;

    @Column
    private String requestDescription;

    @Column
    private LocalDateTime constructionDate;

    @Column
    private Boolean parking;

    @Column
    private Boolean addRequest;

    @Column
    private String requestStatus;

    @ManyToOne
    @JoinColumn(name = "houseUserMappingId", nullable = false)
    private HouseUserMapping houseUserMapping;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
