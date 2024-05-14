package com.company.project.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public interface FileStorageService {

    void init();

    String getStorePath();

    long save(MultipartFile multipartFile, BiConsumer<String, String> biConsumer);

    Resource load(String filename);

    Stream<Path> load();

    void clear();
}