package com.fullstack.proyecto.angular.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "hospedaje")
@Data
public class Hospedaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_hospedaje;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String direccion;
    @Column(name = "precio_noche", nullable = false)
    private Double precioNoche;
    private String tipo;
}