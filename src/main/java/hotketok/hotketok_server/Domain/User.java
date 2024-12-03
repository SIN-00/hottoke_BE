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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String username;

    private String loginId;

    private String role;

    private String email;

    @Column
    private String password;

    @Column
    private String status;

    @Column(name = "profile_image")
    private String profileImage;
}
