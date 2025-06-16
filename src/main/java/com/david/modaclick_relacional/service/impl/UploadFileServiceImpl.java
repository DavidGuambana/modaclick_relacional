package com.david.modaclick_relacional.service.impl;

import com.david.modaclick_relacional.service.UploadFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Value("${local.storage.path}")
    private String storagePath;

    @Override
    public Resource load(String filename) throws MalformedURLException {
        Path filePath = Paths.get(storagePath, filename).toAbsolutePath();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            System.out.println("No se puede leer el archivo en la ruta:"+filePath);
        }
        return resource;
    }


    @Override
    public String copy(MultipartFile file) throws IOException {
        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get(storagePath).toAbsolutePath();
        Path filePath = Paths.get(path.toString(), uniqueFilename);
        try (InputStream inputStream = file.getInputStream()) {
            // Crear el directorio si no existe
            File dir = new File(path.toString());
            if (!dir.exists()) {
                System.out.println("Se crea...");
                Files.createDirectories(filePath.getParent());
            }
            Files.copy(inputStream, filePath.toAbsolutePath());
            System.out.println("Archivo subido a: " + filePath);
            return uniqueFilename;
        } catch (IOException e) {
            System.err.println("Error al subir el archivo: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    public void delete(String filename) {
        if (filename != null && !filename.isEmpty()) {
            if (storagePath == null || storagePath.isEmpty()) {
                throw new IllegalStateException("La ruta de almacenamiento no puede ser nula o vacía.");
            }

            Path filePath = Paths.get(storagePath, filename).toAbsolutePath();
            try {
                if (Files.deleteIfExists(filePath)) {
                    System.out.println("Archivo eliminado: " + filePath);
                } else {
                    System.out.println("El archivo no existe: " + filePath);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al eliminar el archivo: " + filename, e);
            }
        }
    }

    @Override
    public List<String> listFiles() {
        List<String> imageNames = new ArrayList<>();
        File folder = new File(storagePath);

        // Verificar si el directorio existe y es un directorio
        if (folder.exists() && folder.isDirectory()) {
            // Filtrar archivos con extensiones comunes de imágenes
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg"));
            if (files != null) {
                for (File file : files) {
                    imageNames.add(file.getName());  // Añadir el nombre del archivo a la lista
                }
            }
        }
        return imageNames;  // Devolver la lista de nombres de imágenes
    }
}