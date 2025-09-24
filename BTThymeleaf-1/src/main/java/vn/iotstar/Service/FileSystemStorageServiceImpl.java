package vn.iotstar.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import vn.iotstar.Exception.StorageException;
import vn.iotstar.config.StorageProperties;

@Service
public class FileSystemStorageServiceImpl implements IStorageService {

    private final Path rootLocation;

    public FileSystemStorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation()).toAbsolutePath().normalize();
        init();
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map URL /uploads/** đến thư mục thật
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + "E:/upload" + "/");
    }
    @Override
    public String getStorageFilename(MultipartFile file, String id) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        return "uploads/" + id + "." + ext;
    }

    @Override
    public void store(MultipartFile file, String storeFilename) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file");
            }

            // storeFilename dạng "uploads/p123.jpg"
            Path destinationFile = this.rootLocation.resolve(storeFilename)
                    .normalize().toAbsolutePath();

            // Kiểm tra không cho ghi file ra ngoài thư mục gốc
            if (!destinationFile.startsWith(this.rootLocation)) {
                throw new StorageException("Cannot store file outside current directory");
            }

            // Tạo thư mục nếu chưa có
            Files.createDirectories(destinationFile.getParent());

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception e) {
            throw new StorageException("Failed to store file: ", e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            throw new StorageException("Cannot read file: " + filename);
        } catch (Exception e) {
            throw new StorageException("Could not read file: " + filename, e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename).normalize();
    }

    @Override
    public void delete(String storeFilename) throws Exception {
        Path destinationFile = rootLocation.resolve(storeFilename).normalize().toAbsolutePath();
        Files.deleteIfExists(destinationFile);
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
            System.out.println("Upload folder initialized at: " + rootLocation);
        } catch (Exception e) {
            throw new StorageException("Could not initialize storage: ", e);
        }
    }
}
