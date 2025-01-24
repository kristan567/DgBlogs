package com.example.Blog_Application2.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface FileService {

    String uploadImage(String path, MultipartFile file);

    InputStream getResource(String path, String filename ) throws FileNotFoundException;
}
