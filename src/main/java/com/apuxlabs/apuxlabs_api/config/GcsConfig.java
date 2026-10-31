package com.apuxlabs.apuxlabs_api.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class GcsConfig {

    @Value("${spring.cloud.gcp.project-id}")
    private String projectId;

    @Value("${spring.cloud.gcp.credentials.location:}")
    private String credentialsLocation;

    @Bean
    public Storage googleCloudStorage() throws IOException {
        var builder = StorageOptions.newBuilder().setProjectId(projectId);
        if (credentialsLocation != null && !credentialsLocation.isBlank()) {
            var resource = new org.springframework.core.io.DefaultResourceLoader().getResource(credentialsLocation);
            builder.setCredentials(GoogleCredentials.fromStream(resource.getInputStream()));
        }
        // With no explicit credentials, Google Cloud uses Application Default
        // Credentials when an upload is actually attempted. This keeps local
        // registration and worklist APIs available without GCS secrets.
        return builder.build().getService();
    }
}
