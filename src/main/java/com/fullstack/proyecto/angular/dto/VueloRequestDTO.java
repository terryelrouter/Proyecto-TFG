package com.fullstack.proyecto.angular.dto;

import com.fullstack.proyecto.angular.enums.ClaseEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VueloRequestDTO {
    private Long idVuelo;
    private String aerolinea;
    private String num_vuelo;
    private LocalDateTime fecha_salida;
    private LocalDateTime fecha_llegada;
    private String origen;
    private String destino;
    private ClaseEnum clase;
    private Double precio_vuelo;
}
