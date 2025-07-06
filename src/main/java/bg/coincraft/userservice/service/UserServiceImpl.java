package bg.coincraft.userservice.service;

import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.db.UserEntity;
import bg.coincraft.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity create(CreateUserDTO createUserDTO) {
        UserEntity user = UserEntity.builder()
                .setUsername(createUserDTO.getUsername())
                .setEmail(createUserDTO.getEmail())
                .setActive(true)
                .setCreatedAt(LocalDateTime.now())
                .build();
        return this.userRepository.save(user);
    }
}
