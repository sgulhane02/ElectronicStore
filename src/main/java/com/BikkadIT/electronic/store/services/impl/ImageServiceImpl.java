package com.BikkadIT.electronic.store.services.impl;

import com.BikkadIT.electronic.store.exceptions.BadApiRequestException;
import com.BikkadIT.electronic.store.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Override
        public String uploadFile(MultipartFile file, String path) throws IOException {
            String originalFilename = file.getOriginalFilename();
            log.info("fileName :{}" ,originalFilename);
            String filename = UUID.randomUUID().toString();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileNameWithExtension = filename + extension;
            String fullPathWithFileName=path+fileNameWithExtension;
            log.info("Full image path :{}",fullPathWithFileName);

            if(extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase(".jpeg")){

                log.info("file extension is :{}",extension);
                File folder = new File(path);
                if(!folder.exists())
                {
                    folder.mkdirs();
                }
                Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
                return fileNameWithExtension;
            }
            else{
                throw new BadApiRequestException("File with this "+extension+" Not allowed");
            }

        }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath=path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
    }

