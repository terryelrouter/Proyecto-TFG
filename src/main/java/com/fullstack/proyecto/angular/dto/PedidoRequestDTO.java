package com.fullstack.proyecto.angular.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class PedidoRequestDTO {
    private LocalDateTime fechaPedido;
    private Double precioTotal;
    private Integer numeroPersonas;
    private String metodoPago;
    private String estado;
    private Long idCliente;
}