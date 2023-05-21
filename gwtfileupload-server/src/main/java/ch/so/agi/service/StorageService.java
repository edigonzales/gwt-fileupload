package ch.so.agi.service;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();

    Path store(MultipartFile file);
    
    Path load(String filename);

    void delete(String filename);
}
