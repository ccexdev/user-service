package bg.coincraft.userservice.web;

import bg.coincraft.userservice.model.UserRequestDTO;
import bg.coincraft.userservice.model.db.UserEntity;
import bg.coincraft.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDelegate implements UserApiDelegate {

    private final UserService userService;

    public ResponseEntity<Void> createUser(UserRequestDTO requestDTO) {
        UserEntity userEntity = this.userService.create(requestDTO);
        return ResponseEntity.ok().build();
    }
}
