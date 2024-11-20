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
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private Long houseId;

    @Column(name = "house_address", nullable = false)
    private String houseAddress;

    @Column(name = "unit_code")
    private String unitCode;

    @Column(name = "monthly_rent")
    private Double monthlyRent;

    @Column(name = "maintenance_cost")
    private Double maintenanceCost;
}