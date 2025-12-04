package dev.srello.cocinillas.core.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

import static software.amazon.awssdk.regions.Region.of;

@Configuration
public class R2Config {
    @Value("${r2.accountId}")
    private String accountId;

    @Value("${r2.accessKey}")
    private String accessKey;

    @Value("${r2.secretKey}")
    private String secretKey;

    @Value("${r2.region}")
    private String region;

    @Bean
    public S3Presigner s3Presigner() {
        AwsBasicCredentials creds = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Presigner.builder()
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .region(of(region))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .endpointOverride(URI.create("https://" + accountId + ".r2.cloudflarestorage.com"))
                .build();
    }

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials creds = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .region(of(region))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .endpointOverride(URI.create("https://" + accountId + ".r2.cloudflarestorage.com"))
                .build();
    }


}
