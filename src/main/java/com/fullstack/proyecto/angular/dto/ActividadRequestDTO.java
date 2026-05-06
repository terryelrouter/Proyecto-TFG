package com.fullstack.proyecto.angular.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActividadRequestDTO {
    private Long idActividad;
    private String nombre;
    private String descripcion;
    private String tipo;
    private Double precio_actividad;
}
