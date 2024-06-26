package mk.com.readify.service.impl;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.com.readify.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${application.file.upload.photos-output-path}")
    private String fileUploadPath;

    @Override
    public String saveFile(@Nonnull MultipartFile file, @Nonnull Long userId) {
        final String fileUploadSubPath = "users" + File.separator + userId;
        return uploadFile(file, fileUploadSubPath);
    }

    private String uploadFile(@Nonnull MultipartFile file, @Nonnull String fileUploadSubPath) {
        final String uploadPath = fileUploadPath + File.separator + fileUploadSubPath;
        File targetFolder = new File(uploadPath);
        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated) {
                log.warn("Failed to create the folder");
                return null;
            }
        }
        final String fileExtension = getFileExtension(file.getOriginalFilename());
        String targetFilePath = uploadPath + File.separator + System.currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath, file.getBytes());
            log.info("File saved to " + targetFilePath);
            return targetFilePath;
        } catch (IOException e) {
            log.error("Failed to save the file", e);
        }
        return null;
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return filename.substring(lastDotIndex + 1).toLowerCase();
    }
}