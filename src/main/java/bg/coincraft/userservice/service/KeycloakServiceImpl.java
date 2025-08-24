package bg.coincraft.userservice.service;

import bg.coincraft.userservice.config.KeycloakConfigurationProperties;
import bg.coincraft.userservice.exception.ConstraintViolationException;
import bg.coincraft.userservice.model.CreateUserDTO;
import bg.coincraft.userservice.model.LoginUserDTO;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private static final String LOCATION = "Location";
    private final Keycloak keycloak;
    private final AuthzClient authzClient;
    private final KeycloakConfigurationProperties keycloakConfigurationProperties;

    @Override
    public String create(CreateUserDTO createUserDTO) {
        try (Response response =
                     keycloak.realm(keycloakConfigurationProperties.getUserRealm())
                             .users()
                             .create(getUserRepresentation(createUserDTO))) {
            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                return getKeycloakUserId(response.getHeaderString(LOCATION));
            } else {
                throw new ConstraintViolationException("User creation failed. Response: " + response.readEntity(String.class));
            }
        }
    }

    private static UserRepresentation getUserRepresentation(CreateUserDTO createUserDTO) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(createUserDTO.getUsername());
        userRepresentation.setEmail(createUserDTO.getEmail());
        userRepresentation.setFirstName(createUserDTO.getFirstName());
        userRepresentation.setLastName(createUserDTO.getLastName());
        userRepresentation.setEnabled(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(createUserDTO.getPassword());
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
        return userRepresentation;
    }

    private String getKeycloakUserId(String location) {
        return location.substring(location.lastIndexOf("/") + 1);
    }

    @Override
    public AccessTokenResponse obtainAccessToken(LoginUserDTO loginUserDTO) {
        return authzClient.obtainAccessToken(loginUserDTO.getUsername(), loginUserDTO.getPassword());
    }
}
