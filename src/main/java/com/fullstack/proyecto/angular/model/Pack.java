package com.fullstack.proyecto.angular.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "pack")
@Data
public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pack")
    private Long idPack;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(name = "precio_pack", nullable = false)
    private Double precioPack;

    @Column(nullable = false)
    private Double descuento;

    private Boolean activo = true;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @OneToMany(mappedBy = "pack")
    private List<Viaje> viajes;
}