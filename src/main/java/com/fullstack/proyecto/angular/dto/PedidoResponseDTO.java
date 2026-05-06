package com.fullstack.proyecto.angular.dto;

import com.fullstack.proyecto.angular.enums.EstadoPedidoEnum;
import com.fullstack.proyecto.angular.enums.MetodoPagoEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder

public class PedidoResponseDTO {
    private Long idPedido;
    private LocalDateTime fechaPedido;
    private Double precioTotal;
    private Integer numeroPersonas;
    private MetodoPagoEnum metodoPago;
    private EstadoPedidoEnum estado;
    private Double descuento;

    private Long idCliente;
    private String nombreCliente;
    private String apellidosCLiente;
    private String dniCliente;
    private String emailCliente;
}