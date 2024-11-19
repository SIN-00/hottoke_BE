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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "vendorId", nullable = false)
    private ConstructionVendor vendor;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column
    private String constructCategory;

    @Column
    private Double constructPrice;

    @Column
    private String constructAddress;

    @Column
    private Double rate;

    @Column
    private String review;

    @Column
    private String constructImage;
}
