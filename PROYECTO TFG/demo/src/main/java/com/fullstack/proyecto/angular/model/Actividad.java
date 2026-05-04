package com.fullstack.proyecto.angular.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "actividades")
@Data
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_actividades")
    private Long idActividad;
    @Column(nullable = false)
    private String nombre;
    private String descripcion;
    private String tipo;
    @Column(name = "precio_actividad", nullable = false)
    private Double precioActividad;
}
