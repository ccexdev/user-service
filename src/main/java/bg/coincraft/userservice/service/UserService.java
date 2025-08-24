package bg.coincraft.userservice.service;

import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.LoginUserDTO;
import bg.coincraft.userservice.model.TokenResponseDTO;

public interface UserService {

    void create(CreateUserDTO createUserDTO);
    TokenResponseDTO getToken(LoginUserDTO loginUserDTO);
}
