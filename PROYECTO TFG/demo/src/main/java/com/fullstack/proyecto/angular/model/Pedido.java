package com.fullstack.proyecto.angular.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_pedido")
    private Long idPedido;
    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaPedido;
    @Column(name = "precio_total", nullable = false)
    private Double precioTotal;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "numero_personas", nullable = false)
    private Integer numeroPersonas;
    @Column(name = "metodo_pago", nullable = false)
    private String metodoPago;
    private String estado;

    // Multidestino
    @OneToMany(mappedBy = "pedido")
    private List<Viaje> viajes;
}
