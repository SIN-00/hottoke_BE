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
public class ConstructionVendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;

    @Column(nullable = false)
    private String vendorName;

    @Column
    private String category;

    @Column
    private String callNumber;

    @Column
    private String vendorAddress;

    @Column
    private String notice;

    @Column
    private String news;

    @Column
    private Double rate;
}
