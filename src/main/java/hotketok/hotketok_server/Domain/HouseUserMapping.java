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
    @Column
    private Long house_user_mapping_id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private House house_id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user_id;

    @Column
    private Integer unit_number;
}

