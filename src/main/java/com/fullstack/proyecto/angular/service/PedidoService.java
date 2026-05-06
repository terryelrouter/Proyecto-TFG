package com.fullstack.proyecto.angular.service;

import com.fullstack.proyecto.angular.dto.PedidoRequestDTO;
import com.fullstack.proyecto.angular.dto.PedidoResponseDTO;
import com.fullstack.proyecto.angular.enums.EstadoPedidoEnum;
import com.fullstack.proyecto.angular.model.Cliente;
import com.fullstack.proyecto.angular.model.Pedido;
import com.fullstack.proyecto.angular.model.Viaje;
import com.fullstack.proyecto.angular.repository.ClienteRepository;
import com.fullstack.proyecto.angular.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    private final PedidoRepository pr;
    private final ClienteRepository cr;
    private final ViajeService vs;

    @Autowired
    public PedidoService(PedidoRepository pr,ClienteRepository cr,ViajeService vs){
        this.pr=pr;
        this.cr=cr;
        this.vs=vs;
    }
    
    
    // Map
    private PedidoResponseDTO mapToDTO(Pedido pedido) {
        return PedidoResponseDTO.builder()
                .idPedido(pedido.getIdPedido())
                .fechaPedido(pedido.getFechaPedido())
                .precioTotal(pedido.getPrecioTotal())
                .numeroPersonas(pedido.getNumeroPersonas())
                .metodoPago(pedido.getMetodoPago())
                .estado(pedido.getEstado())
                .idCliente(pedido.getCliente().getIdCliente())
                .nombreCliente(pedido.getCliente().getNombre())
                .emailCliente(pedido.getCliente().getEmail())
                .build();
    }

    //Calcular precio
    private Double calcularPrecioPedido(Pedido pedido) {
        if (Objects.isNull(pedido.getViajes()) || pedido.getViajes().isEmpty()) {
            return 0.0;
        }

        double totalViajes = pedido.getViajes()
                .stream()
                .mapToDouble(vs::calcularPrecioViaje)
                .sum();

        double totalSinDescuento = totalViajes * pedido.getNumeroPersonas();

        // Si hay descuento
        if (Objects.nonNull(pedido.getDescuento()) && pedido.getDescuento() > 0) {
            return totalSinDescuento * (1 - pedido.getDescuento());
        }

        return totalSinDescuento;
    }
    
    // Crear pedido
    public PedidoResponseDTO createPedido(Pedido pedido, Long idCliente) {
        Cliente cliente = cr.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + idCliente));
        pedido.setCliente(cliente);

        // El precio se calcula siempre automáticamente
        pedido.setPrecioTotal(calcularPrecioPedido(pedido));

        return mapToDTO(pr.save(pedido));
    }

    // Listar todos
    public List<PedidoResponseDTO> getAllPedidos() {
        return pr.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public PedidoResponseDTO getPedidoById(Long id) {
        return pr.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
    }

    // Actualizar pedido completo
    public PedidoResponseDTO updatePedido(Long id, Pedido datosNuevos) {
        Pedido pedidoExistente = pr.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));

        if (Objects.nonNull(datosNuevos.getFechaPedido())) {
            pedidoExistente.setFechaPedido(datosNuevos.getFechaPedido());
        }

        if (Objects.nonNull(datosNuevos.getPrecioTotal())) {
            pedidoExistente.setPrecioTotal(datosNuevos.getPrecioTotal());
        }

        if (Objects.nonNull(datosNuevos.getNumeroPersonas())) {
            pedidoExistente.setNumeroPersonas(datosNuevos.getNumeroPersonas());
        }

        if (Objects.nonNull(datosNuevos.getEstado())) {
            pedidoExistente.setEstado(datosNuevos.getEstado());
        }

        if (Objects.nonNull(datosNuevos.getMetodoPago())) {
            pedidoExistente.setMetodoPago(datosNuevos.getMetodoPago());
        }

        if (Objects.nonNull(datosNuevos.getCliente())) {
            pedidoExistente.setCliente(datosNuevos.getCliente());
        }
        if (Objects.nonNull(datosNuevos.getDescuento())) {
            pedidoExistente.setDescuento(datosNuevos.getDescuento());
        }

// Recalcular precio si cambia descuento o numeroPersonas
        pedidoExistente.setPrecioTotal(calcularPrecioPedido(pedidoExistente));

        return mapToDTO(pr.save(pedidoExistente));
    }

    // Actualizar solo el estado
    public PedidoResponseDTO updateEstado(Long id, EstadoPedidoEnum nuevoEstado) {
        Pedido pedido = pr.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
        pedido.setEstado(nuevoEstado);
        return mapToDTO(pr.save(pedido));
    }

    // Eliminar
    public void deletePedido(Long id) {
        if (!pr.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado con id: " + id);
        }
        pr.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> getPedidosByCliente(Long idCliente) {
        return pr.findByCliente_IdCliente(idCliente).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    
}
