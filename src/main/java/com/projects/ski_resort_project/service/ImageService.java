package com.projects.ski_resort_project.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;


@Service
public class ImageService {
    private final String bucket;

    public ImageService(@Value("${app.image.bucket}") String bucket) {
        this.bucket = bucket;
    }

    @SneakyThrows
    public void upload(String imagePath, InputStream image) {
        Path path = Path.of(bucket, imagePath);
        try (image) {
            Files.createDirectories(path.getParent());
            Files.write(path, image.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    public Optional<byte[]> getImage(String imagePath) {
        Path path = Path.of(bucket, imagePath);
        return Optional.of(path)
                .filter(Files::exists)
                .map(this::readImage);
    }

    @SneakyThrows
    private byte[] readImage(Path path) {
        return Files.readAllBytes(path);
    }
}
