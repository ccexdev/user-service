package bg.coincraft.userservice.service.keycloak;

import bg.coincraft.userservice.exception.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements KeycloakUserService {

    private final Keycloak keycloak;

    @Override
    public String createUser(String realmName, UserRepresentation userRep) {
        RealmResource realm = keycloak.realm(realmName);
        try (Response response = realm.users().create(userRep)) {
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                throw new ConstraintViolationException("User creation failed. Response: " + response.readEntity(String.class));
            }
            return extractUserId(response.getHeaderString("Location"));
        }
    }

    private String extractUserId(String location) {
        return location.substring(location.lastIndexOf('/') + 1);
    }

    @Override
    public void assignRole(String realmName, String userId, String roleName) {
        RealmResource realm = keycloak.realm(realmName);
        RoleRepresentation role = realm.roles().get(roleName).toRepresentation();
        UserResource user = realm.users().get(userId);
        user.roles().realmLevel().add(Collections.singletonList(role));
    }
}
