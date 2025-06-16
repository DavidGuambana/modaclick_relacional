package com.david.modaclick_relacional.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface UploadFileService {

    public Resource load(String filename) throws MalformedURLException;

    public String copy(MultipartFile file) throws IOException;

    public void delete(String filename);

    // Método para listar todos los archivos
    public List<String> listFiles();

}