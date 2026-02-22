package bg.coincraft.userservice.web;

import bg.coincraft.userservice.IntegrationTestInit;
import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.GenderDTO;
import bg.coincraft.userservice.model.UserProfileDTO;
import bg.coincraft.userservice.model.db.UserEntity;
import bg.coincraft.userservice.model.db.UserProfileEntity;
import bg.coincraft.userservice.model.enums.GenderEnum;
import bg.coincraft.userservice.model.enums.UserRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openapitools.client.api.AuthenticationApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserDelegateIntegrationTest extends IntegrationTestInit {

    @Autowired
    private UserDelegate userDelegate;
    @Autowired
    private EntityManager entityManager;
    @MockitoBean
    private AuthenticationApi authenticationApi;
    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    @DisplayName("""
            WHEN: User is trying to register
            THEN: User should be registered and should be saved to the database
            """)
    public void test1() {
        when(authenticationApi.register(any())).thenReturn("keycloak-123");
        UserEntity expectedUserEntity = expectedUserEntity();
        userDelegate.register(buildCreateUserDTO());

        UserEntity actualUserEntity = getUser("test");

        System.out.println("UserEntity: " + actualUserEntity);

        assertThat(actualUserEntity)
                .usingRecursiveComparison()
                .ignoringFields(
                        "id",
                        "password",
                        "createdAt",
                        "lastLoginAt",
                        "userProfileEntity.id",
                        "userProfileEntity.userEntity")
                .isEqualTo(expectedUserEntity);
    }

    private UserEntity expectedUserEntity() {
        UserEntity userEntity = UserEntity.builder()
                .setUsername("test")
                .setFirstName("Test")
                .setLastName("Testov")
                .setKeycloakId("keycloak-123")
                .setEmail("test@test.com")
                .setIsEmailVerified(false)
                .setPhoneNumber("+359888000111")
                .setActive(false)
                .setCreatedBy("SYSTEM")
                .setRole(UserRole.USER)
                .build();

        UserProfileEntity userProfileEntity = UserProfileEntity.builder()
                .setUserEntity(userEntity)
                .setCountryCode("BUL")
                .setDateOfBirth(LocalDate.of(2000, 1, 1))
                .setGender(GenderEnum.MALE)
                .setAddress("Somewhere 12A")
                .setCity("Sofia")
                .setPostalCode("1000")
                .setCountry("Bulgaria")
                .build();

        userEntity.setUserProfileEntity(userProfileEntity);

        return userEntity;
    }

    private CreateUserDTO buildCreateUserDTO() {
        return CreateUserDTO.builder()
                .setUsername("test")
                .setPassword("123456ASDqwe22qqdc")
                .setFirstName("Test")
                .setLastName("Testov")
                .setEmail("test@test.com")
                .setPhoneNumber("+359888000111")
                .setProfile(UserProfileDTO.builder()
                        .setCountryCode("BUL")
                        .setDateOfBirth(LocalDate.of(2000, 1, 1))
                        .setGender(GenderDTO.MALE)
                        .setAddress("Somewhere 12A")
                        .setCity("Sofia")
                        .setPostalCode("1000")
                        .setCountry("Bulgaria")
                        .build())
                .build();
    }

    private UserEntity getUser(String username) {
        return entityManager.createQuery("""
                                SELECT u
                                FROM UserEntity u
                                WHERE u.username = :username
                                """, UserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
