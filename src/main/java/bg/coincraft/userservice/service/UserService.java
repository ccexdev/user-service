package bg.coincraft.userservice.service;

import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.db.UserEntity;

public interface UserService {

    UserEntity create(CreateUserDTO createUserDTO);
}
