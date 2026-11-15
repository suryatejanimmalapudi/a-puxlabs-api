package com.apuxlabs.apuxlabs_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.apuxlabs.apuxlabs_api.security.LabStaffGatekeeperFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableScheduling
public class SecurityConfig {

    private final LabStaffGatekeeperFilter labStaffGatekeeperFilter;

    public SecurityConfig(LabStaffGatekeeperFilter labStaffGatekeeperFilter) {
        this.labStaffGatekeeperFilter = labStaffGatekeeperFilter;
    }

    @Bean
    FilterRegistrationBean<LabStaffGatekeeperFilter> disableServletRegistration(LabStaffGatekeeperFilter filter) {
        FilterRegistrationBean<LabStaffGatekeeperFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    private static final String GOOGLE_ISSUER = "https://accounts.google.com";
    private static final String GOOGLE_JWKS_URI = "https://www.googleapis.com/oauth2/v3/certs";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/error", "/swagger-ui/**", "/v3/api-docs/**", "/public/reports/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // Public catalogue data is needed by all signed-in staff; catalogue/package changes are admin-only.
                .requestMatchers(HttpMethod.GET, "/api/v1/test-catalog/**", "/api/v1/test-packages/**", "/api/v1/pathologists/**").authenticated()
                .requestMatchers("/api/v1/test-packages/**").hasRole("ADMIN")
                // Reception and administrators manage front-desk, registration, orders and billing.
                .requestMatchers(HttpMethod.GET, "/api/v1/registrations/**", "/api/v1/billing/**").hasAnyRole("ADMIN", "RECEPTIONIST", "TECHNICIAN")
                .requestMatchers("/api/v1/registrations/**", "/api/v1/orders/**", "/api/v1/billing/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                // Laboratory operations are restricted to technicians and administrators.
                .requestMatchers("/api/v1/collections/**", "/api/v1/lab-test-requests/**", "/api/v1/ecg/**").hasAnyRole("ADMIN", "TECHNICIAN")
                .requestMatchers(HttpMethod.POST, "/api/v1/worklist/reports/patient/**").hasAnyRole("ADMIN", "TECHNICIAN")
                .requestMatchers(HttpMethod.GET, "/api/v1/worklist/reports/patient/**").hasAnyRole("ADMIN", "TECHNICIAN", "RECEPTIONIST")
                .requestMatchers(HttpMethod.GET, "/api/v1/worklist/reports/**").hasAnyRole("ADMIN", "TECHNICIAN", "RECEPTIONIST")
                .requestMatchers(HttpMethod.GET, "/api/v1/worklist/pending", "/api/v1/worklist/integration/**").hasAnyRole("ADMIN", "TECHNICIAN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/worklist/*/results", "/api/v1/worklist/reports/*/reject").hasAnyRole("ADMIN", "TECHNICIAN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/worklist/reports/*/verify", "/api/v1/worklist/reports/*/deliver").hasRole("ADMIN")
                .anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}))
            .addFilterAfter(labStaffGatekeeperFilter, BearerTokenAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder(@Value("${google.oauth2.client-id}") String clientId) {
        if (clientId == null || clientId.isBlank()) {
            throw new IllegalStateException("GOOGLE_CLIENT_ID must be set before starting the API");
        }

        NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(GOOGLE_JWKS_URI).build();
        OAuth2TokenValidator<Jwt> issuerValidator = JwtValidators.createDefaultWithIssuer(GOOGLE_ISSUER);
        OAuth2TokenValidator<Jwt> audienceValidator = token -> token.getAudience().contains(clientId)
            ? OAuth2TokenValidatorResult.success()
            : OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", "Token audience does not match this application", null));
        decoder.setJwtValidator(token -> {
            OAuth2TokenValidatorResult issuerResult = issuerValidator.validate(token);
            return issuerResult.hasErrors() ? issuerResult : audienceValidator.validate(token);
        });
        return decoder;
    }
}
