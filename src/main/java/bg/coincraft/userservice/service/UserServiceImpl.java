package bg.coincraft.userservice.service;

import bg.coincraft.userservice.exception.ConstraintViolationException;
import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.LoginUserDTO;
import bg.coincraft.userservice.model.TokenResponseDTO;
import bg.coincraft.userservice.model.db.UserEntity;
import bg.coincraft.userservice.model.enums.UserRole;
import bg.coincraft.userservice.repository.UserRepository;
import bg.coincraft.userservice.service.keycloak.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
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
    private final KeycloakService keycloakService;

    @Override
    public void create(CreateUserDTO createUserDTO) {
        CompromisedPasswordDecision checkedPassword = compromisedPasswordChecker.check(createUserDTO.getPassword());
        if (checkedPassword.isCompromised()) {
            throw new ConstraintViolationException("Password compromised");
        }

        String keycloakUserId = keycloakService.create(createUserDTO);

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

    @Override
    public TokenResponseDTO getToken(LoginUserDTO loginUserDTO) {
        AccessTokenResponse accessTokenResponse = keycloakService.obtainAccessToken(loginUserDTO);

        return TokenResponseDTO.builder()
                .setAccessToken(accessTokenResponse.getToken())
                .setExpiresIn(accessTokenResponse.getExpiresIn())
                .setRefreshToken(accessTokenResponse.getRefreshToken())
                .setRefreshTokenExpiresIn(accessTokenResponse.getRefreshExpiresIn())
                .build();
    }
}
