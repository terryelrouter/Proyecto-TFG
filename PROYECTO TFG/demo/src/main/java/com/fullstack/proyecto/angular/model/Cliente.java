package com.fullstack.proyecto.angular.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "cliente")
@Data

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cliente")
    private Long idCliente;
    @Column(unique = true)
    private String dni;
    @Column(unique = true)
    private String pasaporte;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidos;
    private String telefono;
    private String direccion;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;




}
