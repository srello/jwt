package dev.srello.cocinillas.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.util.List;
import java.util.Objects;

import static java.time.Duration.ofMinutes;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    public static final String CACHE = "public, max-age=3600, immutable";
    private final S3Presigner presigner;
    private final S3Client client;

    @Value("${r2.bucket:cocinillas-app}")
    private String bucket;

    @Value("${r2.presigned-url-duration:5}")
    private Integer durationMinutes;

    @Override
    public URL getPresignedGetURL(String key) {
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest -> getObjectRequest.bucket(bucket).key(key).responseCacheControl(CACHE))
                .signatureDuration(ofMinutes(durationMinutes))
                .build();

        return presigner.presignGetObject(presignRequest).url();
    }

    @Override
    public URL getPresignedPutURL(String key, String contentType) {
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putObjectRequest -> putObjectRequest.bucket(bucket).key(key).contentType(contentType).cacheControl(CACHE))
                .signatureDuration(ofMinutes(durationMinutes))
                .build();

        return presigner.presignPutObject(presignRequest).url();
    }

    @Override
    public void deleteObjects(List<String> keys) {
        List<ObjectIdentifier> objects = keys.stream()
                .filter(Objects::nonNull)
                .map(key -> ObjectIdentifier.builder()
                        .key(key)
                        .build())
                .toList();

        client.deleteObjects(deleteObjectsRequest -> deleteObjectsRequest
                .bucket(bucket)
                .delete(deleteObjectRequest -> deleteObjectRequest.objects(objects)));
    }
}
