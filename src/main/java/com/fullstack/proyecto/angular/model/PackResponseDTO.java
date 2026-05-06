package com.fullstack.proyecto.angular.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PackResponseDTO {
    private Long idPack;
    private String nombre;
    private String descripcion;
    private Double precioPack;
    private Double descuento;
    private Boolean activo;
    private String imagenUrl;
    private List<ViajeResponseDTO> viajes;
}