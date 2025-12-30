package bg.coincraft.userservice.web;

import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDelegate implements UserApiDelegate {

    private final UserService userService;

    // POST /api/v1/user-service/public/register : Register user
    public ResponseEntity<Void> register(CreateUserDTO createUserDTO) {
        this.userService.create(createUserDTO);
        return ResponseEntity.ok().build();
    }
}
