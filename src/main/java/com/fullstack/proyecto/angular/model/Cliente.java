package com.fullstack.proyecto.angular.model;

import com.fullstack.proyecto.angular.enums.RolEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "cliente")
@Data

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;
    @Column(unique = true, nullable = false)
    private String dni;
    @Column(unique = true)
    private String pasaporte;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidos;
    @Column(unique = true)
    private String telefono;
    @Column(nullable = false)
    private String direccion;
    @Column(unique = true, nullable = false)
    @Email
    private String email;
    @Column(nullable = false, name = "contrasena")
    private String contrasenya;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RolEnum rol = RolEnum.CLIENTE;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;




}
