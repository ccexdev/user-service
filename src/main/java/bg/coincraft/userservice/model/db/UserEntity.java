package bg.coincraft.userservice.model.db;

import bg.coincraft.userservice.model.enums.UserRole;
import bg.coincraft.userservice.model.enums.converter.UserRoleConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    @ToString.Exclude
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String keycloakId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean active;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime lastLoginAt;

    @Column
    @Convert(converter = UserRoleConverter.class)
    private UserRole role;
}
