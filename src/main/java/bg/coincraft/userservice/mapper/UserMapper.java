package bg.coincraft.userservice.mapper;

import bg.coincraft.authenticationservice.client.model.UserDTO;
import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.db.UserEntity;
import bg.coincraft.userservice.model.db.UserProfileEntity;
import bg.coincraft.userservice.model.enums.GenderEnum;
import bg.coincraft.userservice.model.enums.UserRole;

import java.time.LocalDateTime;

public class UserMapper {

    public static UserEntity toUserEntity(CreateUserDTO createUserDTO, String keycloakUserId) {
        return UserEntity.builder()
                .setUsername(createUserDTO.getUsername())
                .setFirstName(createUserDTO.getFirstName())
                .setLastName(createUserDTO.getLastName())
                .setKeycloakId(keycloakUserId)
                .setEmail(createUserDTO.getEmail())
                .setIsEmailVerified(false)
                .setPhoneNumber(createUserDTO.getPhoneNumber())
                .setActive(false)
                .setCreatedAt(LocalDateTime.now())
                .setCreatedBy("SYSTEM")
                .setRole(UserRole.USER)
                .build();
    }

    public static UserProfileEntity toUserProfilerEntity(CreateUserDTO createUserDTO, UserEntity user) {
        return UserProfileEntity.builder()
                .setUserEntity(user)
                .setCountryCode(createUserDTO.getProfile().getCountryCode())
                .setDateOfBirth(createUserDTO.getProfile().getDateOfBirth())
                .setGender(GenderEnum.valueOf(createUserDTO.getProfile().getGender().name()))
                .setAddress(createUserDTO.getProfile().getAddress())
                .setCity(createUserDTO.getProfile().getCity())
                .setPostalCode(createUserDTO.getProfile().getPostalCode())
                .setCountry(createUserDTO.getProfile().getCountry())
                .build();
    }

    public static UserDTO toKeycloakUserDto(CreateUserDTO createUserDTO) {
        return UserDTO.builder()
                .setUsername(createUserDTO.getUsername())
                .setPassword(createUserDTO.getPassword())
                .setEmail(createUserDTO.getEmail())
                .setFirstName(createUserDTO.getFirstName())
                .setLastName(createUserDTO.getLastName())
                .build();
    }
}
