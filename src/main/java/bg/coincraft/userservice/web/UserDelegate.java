package bg.coincraft.userservice.web;

import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.LoginUserDTO;
import bg.coincraft.userservice.model.TokenResponseDTO;
import bg.coincraft.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDelegate implements UserApiDelegate {

    private final UserService userService;

    public ResponseEntity<Void> register(CreateUserDTO createUserDTO) {
        this.userService.create(createUserDTO);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<TokenResponseDTO> token(LoginUserDTO loginUserDTO) {
        TokenResponseDTO token = userService.getToken(loginUserDTO);
        return ResponseEntity.ok(token);
    }
}
