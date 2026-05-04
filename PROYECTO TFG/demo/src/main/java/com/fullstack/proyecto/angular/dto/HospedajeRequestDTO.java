package com.fullstack.proyecto.angular.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HospedajeRequestDTO {
    private Long idHospedaje;
    private String nombre;
    private String direccion;
    private Double precio_noche;
    private String tipo;
}
