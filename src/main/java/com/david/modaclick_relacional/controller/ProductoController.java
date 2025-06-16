package com.david.modaclick_relacional.controller;

import com.david.modaclick_relacional.dto.ProductoRequestDTO;
import com.david.modaclick_relacional.model.Producto;
import com.david.modaclick_relacional.service.ProductoService;
import com.david.modaclick_relacional.service.UploadFileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final UploadFileService uploadFileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> crearProductoConImagen(
            @RequestPart("producto") @Valid ProductoRequestDTO productoDTO,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            String fileId = null; // Declarar fuera del if

            // Si se proporciona el archivo y no está vacío, subirlo
            if (file != null && !file.isEmpty()) {
                fileId = uploadFileService.copy(file); // espera un MultipartFile
            }

            // Guardar el producto (con o sin imagen)
            Producto producto = productoService.save(productoDTO, fileId);

            return new ResponseEntity<>(producto, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
