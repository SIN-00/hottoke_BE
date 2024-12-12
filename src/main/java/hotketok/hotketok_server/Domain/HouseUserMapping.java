package hotketok.hotketok_server.Domain;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private String unitNumber;
}


