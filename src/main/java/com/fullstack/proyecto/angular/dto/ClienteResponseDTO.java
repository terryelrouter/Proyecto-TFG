package com.fullstack.proyecto.angular.dto;

import com.fullstack.proyecto.angular.enums.RolEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteResponseDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
    private RolEnum rol;
}
