package hotketok.hotketok_server.Domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorRequestMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorRequestMappingId;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private ServiceRequest request;

    @ManyToOne
    @JoinColumn(name = "vendorId", nullable = false)
    private ConstructionVendor vendor;

    @Column
    private Integer status;

    @Column
    private String estimatePrice;

    @Column
    private String estimateTime;

    @Column
    private String additionalComment;
}
