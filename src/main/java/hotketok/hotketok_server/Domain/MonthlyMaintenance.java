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
@Setter
public class MonthlyMaintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maintenanceCostId;

    private Double normalMaintenance;
    private Double disinfectionCost;
    private Double elevatorMaintenanceCost;
    private Double repairCost;
    private Double fireInsuranceFee;
    private Double cleaningCost;
    private Double longTermRepairReserve;
    private Double foodWasteDisposalFee;
    private Double securityCost;

    @OneToMany(mappedBy = "monthlyMaintenance", cascade = CascadeType.ALL)
    private List<UserMaintenanceMapping> userMaintenanceMappings;

    private LocalDateTime month;
}
