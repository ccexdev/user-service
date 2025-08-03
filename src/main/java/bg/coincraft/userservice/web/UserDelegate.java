package bg.coincraft.userservice.web;

import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.LoginUserDTO;
import bg.coincraft.userservice.model.db.UserEntity;
import bg.coincraft.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDelegate implements UserApiDelegate {

    private final UserService userService;

    public ResponseEntity<Void> register(CreateUserDTO createUserDTO) {
        UserEntity userEntity = this.userService.create(createUserDTO);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> login(LoginUserDTO loginUserDTO) {

        return ResponseEntity.ok().build();
    }
}
