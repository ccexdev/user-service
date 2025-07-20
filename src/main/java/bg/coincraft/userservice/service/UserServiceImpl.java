package bg.coincraft.userservice.service;

import bg.coincraft.userservice.exception.ConstraintViolationException;
import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.db.UserEntity;
import bg.coincraft.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    @Override
    public UserEntity create(CreateUserDTO createUserDTO) {
        CompromisedPasswordDecision checkedPassword = compromisedPasswordChecker.check(createUserDTO.getPassword());
        if (checkedPassword.isCompromised()) {
            throw new ConstraintViolationException("Password compromised");
        }

        UserEntity user = UserEntity.builder()
                .setUsername(createUserDTO.getUsername())
                .setPassword(passwordService.encrypt(createUserDTO.getPassword()))
                .setEmail(createUserDTO.getEmail())
                .setActive(true)
                .setCreatedAt(LocalDateTime.now())
                .build();
        return this.userRepository.save(user);
    }
}
