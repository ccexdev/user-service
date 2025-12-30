package bg.coincraft.userservice.config;

import bg.coincraft.authenticationservice.client.ApiClient;
import org.openapitools.client.api.AuthenticationApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AuthenticationApiConfig {

    @Value("${services.authentication.url}")
    private String authenticationServiceUrl;

    /**
     * Create RestTemplate with interceptors and buffering
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();

        // Add buffering to allow reading response multiple times
        restTemplate.setRequestFactory(
                new BufferingClientHttpRequestFactory(restTemplate.getRequestFactory())
        );

        return restTemplate;
    }

    @Bean
    public ApiClient authenticationApiClient(RestTemplate restTemplate) {
        ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath(authenticationServiceUrl);
        return apiClient;
    }

    @Bean
    public AuthenticationApi authenticationApi(ApiClient apiClient) {
        return new AuthenticationApi(apiClient);
    }
}
