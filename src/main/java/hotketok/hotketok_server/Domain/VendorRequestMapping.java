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
public class VendorRequestMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorRequestMappingId;

    @ManyToOne
    @JoinColumn(name = "requestId", nullable = false)
    private ServiceRequest request;

    @ManyToOne
    @JoinColumn(name = "vendorId", nullable = false)
    private ConstructionVendor vendor;

    @Column
    private String status;

    @Column
    private String estimate_price;

    @Column
    private String estimate_time;

    @Column
    private String additional_comment;
}
