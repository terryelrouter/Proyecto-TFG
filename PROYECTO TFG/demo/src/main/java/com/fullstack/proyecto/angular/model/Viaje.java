package com.fullstack.proyecto.angular.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "viaje")
@Data

public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_viaje;

    @Column(nullable = false)
    private String origen;
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;
    @Column(nullable = false)
    private Double precio;


    private String descripcion;
    @Column(name = "tiempo_atm")
    private String tiempoAtm;
    @Column(name = "distancia_entre_vuelos", nullable = false)
    private Integer distanciaEntreVuelos;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_vuelo")
    private Vuelo vuelo;

    @ManyToOne
    @JoinColumn(name = "id_hospedaje")
    private Hospedaje hospedaje;

    @ManyToOne
    @JoinColumn(name = "id_actividades")
    private Actividad actividad;
}
