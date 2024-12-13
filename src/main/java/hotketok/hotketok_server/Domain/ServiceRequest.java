package hotketok.hotketok_server.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> requestImage;

    @Column
    private String requestDescription;

    @Column
    private String category;

    @ManyToOne
    @JoinColumn(name = "houseUserMappingId", nullable = false)
    private HouseUserMapping houseUserMapping;

    @Column
    private LocalDateTime createdAt;

    @Column
    private Integer status;

    @Builder.Default
    @OneToMany(mappedBy = "serviceRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConstructionDate> constructionDates = new ArrayList<>();
}
