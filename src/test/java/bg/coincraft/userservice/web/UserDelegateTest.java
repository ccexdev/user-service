package bg.coincraft.userservice.web;

import bg.coincraft.userservice.model.UserRequestDTO;
import bg.coincraft.userservice.model.db.UserEntity;
import bg.coincraft.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDelegateTest {

    @Mock
    private UserService userService;
    @Test
    void test1() {
        UserEntity expectedUserEntity = createUserEntity();
        when(userService.create(any()))
                .thenReturn(UserEntity.builder()
                        .setUsername("test")
                        .setEmail("test@test.com")
                        .setActive(true)
                        .build());

        UserEntity actualUserEntity = userService.create(UserRequestDTO.builder().build());

        assertThat(actualUserEntity)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "lastLoginAt")
                .isEqualTo(expectedUserEntity);
    }

    private UserEntity createUserEntity() {
        return UserEntity.builder()
                .setUsername("test1")
                .setEmail("test@test.com")
                .setActive(true)
                .build();
    }
}
