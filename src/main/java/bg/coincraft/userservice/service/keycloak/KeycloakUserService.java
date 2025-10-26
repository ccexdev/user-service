package bg.coincraft.userservice.service.keycloak;

import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakUserService {

    String createUser(String realmName, UserRepresentation userRep);

    void assignRole(String realmName, String userId, String roleName);
}
