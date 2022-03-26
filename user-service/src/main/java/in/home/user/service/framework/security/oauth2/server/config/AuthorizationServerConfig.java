package in.home.user.service.framework.security.oauth2.server.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import in.home.user.service.framework.security.oauth2.server.jose.Jwks;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

@Configuration(proxyBeanMethods = false)
@Import(OAuth2AuthorizationServerConfiguration.class)
public class AuthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.formLogin(Customizer.withDefaults())
                .build();
    }

    // @formatter:off
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("user")
                .clientSecret("$2a$10$Nhv66IFmi8NmNB21qMlTCO5T9IDtjMotTDGY1FIE1Vvn8Yky/9OVe")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/user-oidc")
                .redirectUri("http://127.0.0.1:8080/authorized")
                .scope(OidcScopes.OPENID)
                .scope("read")
                .scope("write")
                .scope("admin")
                .clientSettings(
                        ClientSettings
                                .builder()
                                .requireAuthorizationConsent(true)
                                .build())
                .build();

        // // Save registered client in db as if in-memory
        // JdbcRegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        // registeredClientRepository.save(registeredClient);
        //
        // return registeredClientRepository;

        return new InMemoryRegisteredClientRepository(registeredClient);
    }
    // @formatter:on

    // @Bean
    // public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate,
    //         RegisteredClientRepository registeredClientRepository) {
    //     return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    // }

    // @Bean
    // public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate
    // jdbcTemplate,
    //         RegisteredClientRepository registeredClientRepository) {
    //     return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate,
    //     registeredClientRepository);
    // }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
                .issuer("http://auth-server:8090")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // PasswordEncoder passwordEncoder(){
    //     return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    // }

    // @Bean
    // public EmbeddedDatabase embeddedDatabase() {
    //     // @formatter:off
    //     return new EmbeddedDatabaseBuilder()
    //             .generateUniqueName(true)
    //             .setType(EmbeddedDatabaseType.H2)
    //             .setScriptEncoding("UTF-8")
    //             .addScript("org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql")
    //             .addScript("org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql")
    //             .addScript("org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql")
    //             .build();
    //     // @formatter:on
    // }
}
