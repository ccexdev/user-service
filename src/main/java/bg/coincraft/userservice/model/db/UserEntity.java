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

    @Column(unique = true, nullable = false)
    private String keycloakId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private boolean isEmailVerified;

    @Column
    private LocalDateTime emailVerifiedAt;

    @Column
    private String emailVerificationToken;

    @Column
    private LocalDateTime emailVerificationTokenExpiresAt;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private String createdBy;

    @Column
    private String updatedBy;

    @Column
    private LocalDateTime lastLoginAt;

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfileEntity userProfileEntity;

    @Column(nullable = false)
    @Convert(converter = UserRoleConverter.class)
    private UserRole role;
}
