package bg.coincraft.userservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakConfigurationProperties {

   private String serverUrl;
   private String realm;
   private String userRealm;
   private String grantType;
   private String clientId;
   private String userClientId;
   private String username;
   private String password;
}
