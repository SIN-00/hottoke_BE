package hotketok.hotketok_server.Domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserMaintenanceMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userMaintenanceMappingId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "maintenance_cost_id", nullable = false)
    private MonthlyMaintenance monthlyMaintenance;

    private LocalDateTime month;
}
