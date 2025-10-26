package bg.coincraft.userservice.web;

import bg.coincraft.userservice.IntegrationTestInit;
import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.db.UserEntity;
import bg.coincraft.userservice.model.enums.UserRole;
import bg.coincraft.userservice.service.keycloak.KeycloakService;
import bg.coincraft.userservice.service.keycloak.KeycloakUserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserDelegateIntegrationTest extends IntegrationTestInit {

    @Autowired
    private UserDelegate userDelegate;
    @Autowired
    private EntityManager entityManager;
    @MockitoBean
    private KeycloakService keycloakService;
    @MockitoBean
    private KeycloakUserService keycloakUserService;
    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    @DisplayName("""
            WHEN:
            THEN:
            """)
    public void test1() {
        when(keycloakService.create(any())).thenReturn("keycloak-123");
        UserEntity expectedUserEntity = expectedUserEntity();
        userDelegate.register(CreateUserDTO.builder()
                        .setFirstName("Test")
                        .setLastName("Testov")
                        .setUsername("test")
                        .setPassword("123456ASDqwe22qqdc")
                        .setEmail("test@test.com")
                .build());

        UserEntity actualUserEntity = getUser("test");

        assertThat(actualUserEntity)
                .usingRecursiveComparison()
                .ignoringFields(
                        "id",
                        "password",
                        "createdAt",
                        "lastLoginAt")
                .isEqualTo(expectedUserEntity);
    }

    private UserEntity expectedUserEntity() {
        return UserEntity.builder()
                .setFirstName("Test")
                .setLastName("Testov")
                .setUsername("test")
                .setEmail("test@test.com")
                .setKeycloakId("keycloak-123")
                .setRole(UserRole.USER)
                .setActive(true)
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
