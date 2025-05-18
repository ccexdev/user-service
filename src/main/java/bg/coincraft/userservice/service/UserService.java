package bg.coincraft.userservice.service;

import bg.coincraft.userservice.model.UserRequestDTO;
import bg.coincraft.userservice.model.db.UserEntity;

public interface UserService {

    UserEntity create(UserRequestDTO userRequestDTO);
}
