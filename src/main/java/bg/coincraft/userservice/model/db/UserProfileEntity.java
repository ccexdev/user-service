package bg.coincraft.userservice.model.db;

import bg.coincraft.userservice.model.enums.GenderEnum;
import bg.coincraft.userservice.model.enums.converter.GenderEnumConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
@ToString
public class UserProfileEntity {

    @Id
    @Column(name = "user_id")
    private Long id;

    @ToString.Exclude
    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity userEntity;

    @Column(nullable = false)
    private String countryCode;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @Convert(converter = GenderEnumConverter.class)
    private GenderEnum gender;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String country;
}
