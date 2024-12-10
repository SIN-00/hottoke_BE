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
    private Long id;

    private String username;

    private String role;

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;

    @Column(name = "email")
    private String email;

    @Column
    private String password;

    @Column
    private String status;

    @Column
    private String profile_image;
}
