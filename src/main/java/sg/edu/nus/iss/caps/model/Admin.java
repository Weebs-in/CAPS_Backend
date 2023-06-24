package sg.edu.nus.iss.caps.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    @Column(name = "admin_username", columnDefinition = "VARCHAR(20) NOT NULL UNIQUE")
    private String username;

    @Column(name = "admin_password", columnDefinition = "VARCHAR(60) NOT NULL")
    private String password;
}
