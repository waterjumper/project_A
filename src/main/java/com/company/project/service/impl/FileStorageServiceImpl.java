package com.company.project.service.impl;

import com.company.project.common.utils.DataResult;
import com.company.project.common.utils.DateUtils;
import com.company.project.common.utils.ToolUtils;
import com.company.project.entity.SysFilesEntity;
import com.company.project.service.FileStorageService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

@Service("fileStorageService")
public class FileStorageServiceImpl implements FileStorageService {


//    private final Path path = Paths.get("fileStorage");

    private final static String userDirectory = ToolUtils.projectDir() + "/images";

    private final Path path = Paths.get(userDirectory);

    static {
        File file = new File(userDirectory);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public String getStorePath() {
        return path.toAbsolutePath().toString();
    }


    @Override
    public void init() {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public long save(MultipartFile multipartFile, BiConsumer<String, String> biConsumer) {
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            Objects.requireNonNull(originalFilename);

            // 2020/0408
            String createTime = DateUtils.format(new Date(), DateUtils.DATEPATTERN_2);
            String newFile = ToolUtils.nanoTime() + "-" + RandomStringUtils.randomAlphanumeric(8)
                    + ToolUtils.getFileType(originalFilename);

            String url = createTime + "/" + newFile;


            // Path resolve = this.path.resolve(createTime + File.separator + originalFilename);
            Path resolve = this.path.resolve(url);
            Path parent = resolve.getParent();
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            long size = Files.copy(multipartFile.getInputStream(), resolve);
            biConsumer.accept(newFile, url);

            return size;
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error:" + e.getMessage());
        }
    }


    @Override
    public Resource load(String filename) {
        Path file = path.resolve(filename);

        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }

            throw new RuntimeException("Could not read the file.");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error:" + e.getMessage());
        }
    }

    @Override
    public Stream<Path> load() {
        try {
            return Files.walk(this.path, 1)
                    .filter(path -> !path.equals(this.path))
                    .map(this.path::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files.");
        }
    }

    @Override
    public void clear() {
        FileSystemUtils.deleteRecursively(path.toFile());
    }
}  