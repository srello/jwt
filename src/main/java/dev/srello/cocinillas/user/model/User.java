package dev.srello.cocinillas.user.model;

import dev.srello.cocinillas.settings.model.Settings;
import dev.srello.cocinillas.user.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    @Column()
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Role role;
    @OneToOne(cascade = ALL)
    @JoinColumn(name = "settings_id", referencedColumnName = "id", nullable = false)
    private Settings settings;
}
