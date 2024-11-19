package hotketok.hotketok_server.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseUserMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long houseUserMappingId;

    @ManyToOne
    @JoinColumn(name = "houseId", nullable = false)
    private House house;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column
    private Integer unitNumber;
}

