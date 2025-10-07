package dev.srello.cocinillas.storage.service;

import java.net.URL;

public interface StorageService {
    URL getPresignedGetURL(String key);

    URL getPresignedPutURL(String key, String contentType);
}
