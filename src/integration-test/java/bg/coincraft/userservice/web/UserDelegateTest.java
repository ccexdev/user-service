package bg.coincraft.userservice.web;

import bg.coincraft.userservice.IntegrationTestInit;
import bg.coincraft.userservice.model.UserRequestDTO;
import bg.coincraft.userservice.model.db.UserEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDelegateTest extends IntegrationTestInit {

    @Autowired
    private UserDelegate userDelegate;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("""
            WHEN:
            THEN:
            """)
    public void test1() {
        UserEntity expectedUserEntity = expectedUserEntity();
        userDelegate.createUser(UserRequestDTO.builder()
                        .setUsername("test")
                        .setEmail("test@test.com")
                .build());

        UserEntity actualUserEntity = getUser("test");

        assertThat(actualUserEntity)
                .usingRecursiveComparison()
                .ignoringFields(
                        "id",
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
