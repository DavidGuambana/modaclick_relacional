package com.david.modaclick_relacional.service.impl;

import com.david.modaclick_relacional.client.ApiClient;
import com.david.modaclick_relacional.dto.DetallePedidoRequestDTO;
import com.david.modaclick_relacional.dto.PedidoRequestDTO;
import com.david.modaclick_relacional.model.DetallePedido;
import com.david.modaclick_relacional.model.Pedido;
import com.david.modaclick_relacional.model.Producto;
import com.david.modaclick_relacional.model.Usuario;
import com.david.modaclick_relacional.repository.DetallePedidoRepository;
import com.david.modaclick_relacional.repository.PedidoRepository;
import com.david.modaclick_relacional.repository.ProductoRepository;
import com.david.modaclick_relacional.repository.UsuarioRepository;
import com.david.modaclick_relacional.service.PedidoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    private final ApiClient apiClient;

    private static final Logger log = LoggerFactory.getLogger(Pedido.class);


    @Override
    @Transactional
    public Pedido save(PedidoRequestDTO pedidoDTO) {
        // 1. Validar usuario
        Usuario usuario = usuarioRepository.findById(pedidoDTO.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // 2. Crear el pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setPagado(pedidoDTO.isPagado());
        pedido.setDetalles(new ArrayList<>());

        // 3. Procesar detalles
        double total = 0;
        for (DetallePedidoRequestDTO detalleDTO : pedidoDTO.getDetalles()) {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + detalleDTO.getProductoId()));

            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para producto: " + producto.getNombre());
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            double subtotal = detalleDTO.getCantidad() * producto.getPrecio();
            detalle.setSubtotal(subtotal);

            pedido.getDetalles().add(detalle);
            total += subtotal;

            producto.setStock(producto.getStock() - (int) detalleDTO.getCantidad());
            productoRepository.save(producto);
        }

        // 4. Guardar pedido
        pedido.setTotal(total);
        pedido = pedidoRepository.save(pedido);

        // Impresión del objeto Pedido en JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // Enviar a MongoDB (opcional)
        enviarPedidoAMongoDB(pedido);

        return pedido;
    }

    private void enviarPedidoAMongoDB(Pedido pedido) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            String url = apiClient.getBaseUrl() + "/modaclick/pedidos";

            // Convertir el pedido a un Map para modificar el ID
            Map<String, Object> pedidoMap = mapper.convertValue(pedido, new TypeReference<Map<String, Object>>() {});

            // Convertir el ID Long a String
            pedidoMap.put("id", pedido.getId().toString());

            // Convertir a JSON formateado para el log
            String pedidoJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pedidoMap);
            System.out.println("Enviando a MongoDB JSON:");
            System.out.println(pedidoJson);

            // Enviar el Map modificado (con ID como String)
            ResponseEntity<Map> response = apiClient.getRestTemplate()
                    .postForEntity(url, pedidoMap, Map.class);

            // Mostrar código y cuerpo de la respuesta
            System.out.println("Respuesta MongoDB: " + response.getStatusCode());
            System.out.println("Cuerpo de respuesta MongoDB: " + response.getBody());

        } catch (Exception e) {
            System.err.println("Error al enviar a MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }



    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));

        // Eliminar detalles primero
        detallePedidoRepository.deleteAll(pedido.getDetalles());

        // Luego eliminar el pedido
        pedidoRepository.delete(pedido);
    }

}
