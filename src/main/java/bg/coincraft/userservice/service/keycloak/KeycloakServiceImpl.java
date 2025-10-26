package bg.coincraft.userservice.service.keycloak;

import bg.coincraft.userservice.config.KeycloakConfigurationProperties;
import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.LoginUserDTO;
import lombok.RequiredArgsConstructor;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private static final String USER_ROLE = "user";
    private final KeycloakUserService keycloakUserService;
    private final AuthzClient authzClient;
    private final KeycloakConfigurationProperties keycloakConfigurationProperties;

    @Override
    public String create(CreateUserDTO createUserDTO) {
        UserRepresentation userRep = toUserRepresentation(createUserDTO);
        String userId = keycloakUserService.createUser(keycloakConfigurationProperties.getUserRealm(), userRep);
        keycloakUserService.assignRole(keycloakConfigurationProperties.getUserRealm(), userId, USER_ROLE);
        return userId;
    }

    private UserRepresentation toUserRepresentation(CreateUserDTO createUserDTO) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(createUserDTO.getUsername());
        user.setEmail(createUserDTO.getEmail());
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setEnabled(true);

        CredentialRepresentation password = new CredentialRepresentation();
        password.setTemporary(false);
        password.setType(CredentialRepresentation.PASSWORD);
        password.setValue(createUserDTO.getPassword());
        user.setCredentials(Collections.singletonList(password));

        return user;
    }

    @Override
    public AccessTokenResponse obtainAccessToken(LoginUserDTO loginUserDTO) {
        return authzClient.obtainAccessToken(loginUserDTO.getUsername(), loginUserDTO.getPassword());
    }
}
