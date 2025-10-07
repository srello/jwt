package dev.srello.cocinillas.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;

import static java.time.Duration.ofMinutes;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final S3Presigner presigner;

    @Value("${r2.bucket:cocinillas-app}")
    private String bucket;

    @Value("${r2.presigned-url-duration:5}")
    private Integer durationMinutes;

    @Override
    public URL getPresignedGetURL(String key) {
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest -> getObjectRequest.bucket(bucket).key(key))
                .signatureDuration(ofMinutes(durationMinutes))
                .build();

        return presigner.presignGetObject(presignRequest).url();
    }

    @Override
    public URL getPresignedPutURL(String key, String contentType) {
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putObjectRequest -> putObjectRequest.bucket(bucket).key(key).contentType(contentType))
                .signatureDuration(ofMinutes(durationMinutes))
                .build();

        return presigner.presignPutObject(presignRequest).url();
    }
}
