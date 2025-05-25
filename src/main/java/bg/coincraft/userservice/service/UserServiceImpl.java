package bg.coincraft.userservice.service;

import bg.coincraft.userservice.model.UserRequestDTO;
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
    public UserEntity create(UserRequestDTO requestDTO) {
        UserEntity user = UserEntity.builder()
                .setUsername(requestDTO.getUsername())
                .setEmail(requestDTO.getEmail())
                .setActive(true)
                .setCreatedAt(LocalDateTime.now())
                .build();
        return this.userRepository.save(user);
    }
}
