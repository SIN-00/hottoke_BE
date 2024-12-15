package hotketok.hotketok_server.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class HouseUserMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_user_mapping_id")
    private Long houseUserMappingId;

    @ManyToOne(cascade = CascadeType.ALL) // Cascade 추가
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    @ManyToOne(cascade = CascadeType.ALL) // Cascade 추가
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private String unitNumber;

    @OneToMany(mappedBy = "houseUserMapping", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KnockKnock> knockKnocks;
}


