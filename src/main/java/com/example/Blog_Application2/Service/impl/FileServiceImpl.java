package com.example.Blog_Application2.Service.impl;

import com.example.Blog_Application2.Service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) {
        // Validate the file name
        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isEmpty()) {
            throw new RuntimeException("Invalid file name!");
        }

        // Generate unique file name
        String randomID = UUID.randomUUID().toString();
        String fileExtension = originalName.substring(originalName.lastIndexOf("."));
        String uniqueFileName = randomID.concat(fileExtension);

        // Construct full file path
        String filePath = path + File.separator + uniqueFileName;

        // Create directory if it does not exist
        File directory = new File(path);
        System.out.println("Attempting to create directory: " + path);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create directory: " + path);
            }
        }

        // Save the file
        try {
            File destinationFile = new File(filePath);
            Files.copy(file.getInputStream(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }

        return uniqueFileName;
    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
        String fullPath = path+File.separator+filename;
        InputStream is = new FileInputStream(fullPath);


        //db logic to return inputstream
        return is;


    }
}
