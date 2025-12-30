package bg.coincraft.userservice.service;

import bg.coincraft.userservice.model.CreateUserDTO;

public interface UserService {

    void create(CreateUserDTO createUserDTO);
}
