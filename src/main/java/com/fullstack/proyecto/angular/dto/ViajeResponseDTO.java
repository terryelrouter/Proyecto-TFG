package com.fullstack.proyecto.angular.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ViajeResponseDTO {
    private Long idViaje;
    private String origen;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double precio;
    private String descripcion;
    private String tiempoAtm;
    private Integer distanciaEntreVuelos;

    // Del pedido solo el id
    private Long idPedido;

    // De cada relación los datos mínimos útiles
    private String aerolinea;
    private String origenVuelo;
    private String destinoVuelo;
    private String nombreHospedaje;
    private String nombreActividad;
}
