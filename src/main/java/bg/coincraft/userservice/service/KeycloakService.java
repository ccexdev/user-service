package bg.coincraft.userservice.service;

import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.LoginUserDTO;
import org.keycloak.representations.AccessTokenResponse;

public interface KeycloakService {

    String create(CreateUserDTO createUserDTO);
    AccessTokenResponse obtainAccessToken(LoginUserDTO loginUserDTO);
}
