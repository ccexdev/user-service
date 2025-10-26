package bg.coincraft.userservice.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.authorization.client.AuthzClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "keycloak.enabled", havingValue = "true", matchIfMissing = true)
public class KeycloakConfig {

    private final KeycloakConfigurationProperties keycloakConfigurationProperties;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakConfigurationProperties.getServerUrl())
                .realm(keycloakConfigurationProperties.getRealm())
                .grantType(keycloakConfigurationProperties.getGrantType())
                .clientId(keycloakConfigurationProperties.getClientId())
                .username(keycloakConfigurationProperties.getUsername())
                .password(keycloakConfigurationProperties.getPassword())
                .build();
    }

    @Bean
    public AuthzClient authzClient() {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", "");

        org.keycloak.authorization.client.Configuration configuration = new org.keycloak.authorization.client.Configuration(
                keycloakConfigurationProperties.getServerUrl(),
                keycloakConfigurationProperties.getUserRealm(),
                keycloakConfigurationProperties.getUserClientId(),
                clientCredentials,
                null);

        return AuthzClient.create(configuration);
    }
}
