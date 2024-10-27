package tn.isetsf.presence.webThymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import tn.isetsf.presence.sec.repository.AppUserRepo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileService {

    @Value("${upload.location:var/www/html/downloads/}") // Use @Value for upload location
    private String uploadLocation;

    public String uploadFile(MultipartFile file, String username) throws IOException {
        // Validate file size and type (optional)
        // ...

        try {
            // Create a unique filename with UUID
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String photoName = UUID.randomUUID().toString() + extension;

            // Ensure upload directory exists with proper permissions
            Path uploadDir = Paths.get(uploadLocation);
            Files.createDirectories(uploadDir); // Create directories if needed

            // Securely transfer the file
            Path filePath = uploadDir.resolve(photoName);
            file.transferTo(filePath.toFile());

            // Return the relative URL (avoid exposing full server path)
            return "/downloads/" + photoName; // Use relative URL for security
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Failed to upload file: " + e.getMessage());
        }
    }
}

