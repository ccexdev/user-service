package bg.coincraft.userservice.web;

import bg.coincraft.userservice.IntegrationTestInit;
import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.db.UserEntity;
import bg.coincraft.userservice.service.KeycloakService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Disabled
    @Test
    @DisplayName("""
            WHEN:
            THEN:
            """)
    public void test1() {
        when(keycloakService.create(any())).thenReturn("keycloak-123");
        UserEntity expectedUserEntity = expectedUserEntity();
        userDelegate.register(CreateUserDTO.builder()
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
                .setUsername("test")
                .setEmail("test@test.com")
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
