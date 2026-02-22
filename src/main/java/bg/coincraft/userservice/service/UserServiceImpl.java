package bg.coincraft.userservice.service;

import bg.coincraft.userservice.exception.ConstraintViolationException;
import bg.coincraft.userservice.mapper.UserMapper;
import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.db.UserEntity;
import bg.coincraft.userservice.model.db.UserProfileEntity;
import bg.coincraft.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.api.AuthenticationApi;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompromisedPasswordChecker compromisedPasswordChecker;
    private final AuthenticationApi authenticationApi;

    @Override
    @Transactional
    public void create(CreateUserDTO createUserDTO) {
        CompromisedPasswordDecision checkedPassword = compromisedPasswordChecker.check(createUserDTO.getPassword());
        if (checkedPassword.isCompromised()) {
            throw new ConstraintViolationException("Password compromised");
        }

        String keycloakUserId = tryToGetKeycloakUserId(createUserDTO);
        UserEntity user = UserMapper.toUserEntity(createUserDTO, keycloakUserId);
        UserProfileEntity userProfileEntity = UserMapper.toUserProfilerEntity(createUserDTO, user);
        user.setUserProfileEntity(userProfileEntity);

        this.userRepository.save(user);
    }

    private String tryToGetKeycloakUserId(CreateUserDTO createUserDTO) {
        try {
            return authenticationApi.register(UserMapper.toKeycloakUserDto(createUserDTO));
        } catch (Exception e) {
            throw new ConstraintViolationException("User creation failed. Username: " + createUserDTO.getUsername());
        }
    }
}
