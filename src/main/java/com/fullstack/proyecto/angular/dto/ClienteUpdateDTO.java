package com.fullstack.proyecto.angular.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteUpdateDTO {
    private String nombre;
    private String apellidos;
    private String telefono;
    private String direccion;
    private String email;
    private String contrasenya;
}
