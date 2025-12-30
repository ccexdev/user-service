package bg.coincraft.userservice.service;

import bg.coincraft.authenticationservice.client.model.UserDTO;
import bg.coincraft.userservice.exception.ConstraintViolationException;
import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.db.UserEntity;
import bg.coincraft.userservice.model.enums.UserRole;
import bg.coincraft.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.api.AuthenticationApi;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final CompromisedPasswordChecker compromisedPasswordChecker;
    private final AuthenticationApi authenticationApi;

    @Override
    public void create(CreateUserDTO createUserDTO) {
        CompromisedPasswordDecision checkedPassword = compromisedPasswordChecker.check(createUserDTO.getPassword());
        if (checkedPassword.isCompromised()) {
            throw new ConstraintViolationException("Password compromised");
        }

        String keycloakUserId = tryToGetKeycloakUserId(createUserDTO);

        UserEntity user = UserEntity.builder()
                .setUsername(createUserDTO.getUsername())
                .setFirstName(createUserDTO.getFirstName())
                .setLastName(createUserDTO.getLastName())
                .setEmail(createUserDTO.getEmail())
                .setActive(true)
                .setCreatedAt(LocalDateTime.now())
                .setRole(UserRole.USER)
                .setKeycloakId(keycloakUserId)
                .build();

        this.userRepository.save(user);
    }

    private String tryToGetKeycloakUserId(CreateUserDTO createUserDTO) {
        try {
            return authenticationApi.register(buildUserDto(createUserDTO));
        } catch (Exception e) {
            throw new ConstraintViolationException("User creation failed. Username: " + createUserDTO.getUsername());
        }
    }

    private UserDTO buildUserDto(CreateUserDTO createUserDTO) {
        return UserDTO.builder()
                .setUsername(createUserDTO.getUsername())
                .setPassword(createUserDTO.getPassword())
                .setEmail(createUserDTO.getEmail())
                .setFirstName(createUserDTO.getFirstName())
                .setLastName(createUserDTO.getLastName())
                .build();
    }
}
