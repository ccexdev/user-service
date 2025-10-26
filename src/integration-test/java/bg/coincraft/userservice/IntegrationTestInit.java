package bg.coincraft.userservice;

import bg.coincraft.userservice.config.KeycloakConfig;
import org.keycloak.admin.client.Keycloak;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest(properties = {
        "spring.main.allow-bean-definition-overriding=true"},
        classes = {UserServiceApplication.class, IntegrationTestInit.TestKeycloakConfig.class})
public class IntegrationTestInit {
    @Container
    protected static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
            new PostgreSQLContainer<>("postgres:latest")
                    .withCreateContainerCmdModifier(
                            cmd -> cmd.withName("postgresql-test-container"))
                    .withExposedPorts(3306, 5432)
                    .withLabel("name", "postgres-test-container")
                    .withDatabaseName("user_service_test")
                    .withUsername("test_user")
                    .withPassword("test_password");

    @DynamicPropertySource
    private static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }


    @TestConfiguration
    public static class TestKeycloakConfig {
        @Bean
        public Keycloak keycloak() {
            return org.mockito.Mockito.mock(Keycloak.class);
        }
    }
}
