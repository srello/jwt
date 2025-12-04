package dev.srello.cocinillas.storage.service;

import java.net.URL;
import java.util.List;

public interface StorageService {
    URL getPresignedGetURL(String key);

    URL getPresignedPutURL(String key, String contentType);

    void deleteObjects(List<String> key);
}
