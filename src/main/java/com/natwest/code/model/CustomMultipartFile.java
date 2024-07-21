package com.natwest.code.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class CustomMultipartFile implements MultipartFile {

    private final byte[] fileContents;
    private final String fileName;

    private final String mediaType;


    public CustomMultipartFile(byte[] fileContents, String fileName, String mediaType) {
        this.fileContents = fileContents;
        this.fileName = fileName;
        this.mediaType = mediaType;
    }

    @Override
    public String getName() {
        return this.fileName;
    }

    @Override
    public String getOriginalFilename() {
        return getName();
    }

    @Override
    public String getContentType() {
        return this.mediaType;
    }

    @Override
    public boolean isEmpty() {
        return fileContents == null || fileContents.length == 0;
    }

    @Override
    public long getSize() {
        return fileContents.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return fileContents;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileContents);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try(OutputStream stream = new FileOutputStream(dest)){
            stream.write(fileContents);
        }

    }
}
