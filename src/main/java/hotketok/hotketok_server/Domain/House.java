package hotketok.hotketok_server.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id", nullable = false)
    private Long houseId;

    @Column
    private String houseAddress;

    @Column
    private String unitCode;

    @Column
    private Double monthlyRent;

    @Column
    private Double maintenanceCost;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HouseUserMapping> houseUserMappings;

}