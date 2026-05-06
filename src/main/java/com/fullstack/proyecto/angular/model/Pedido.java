package com.fullstack.proyecto.angular.model;

import com.fullstack.proyecto.angular.enums.EstadoPedidoEnum;
import com.fullstack.proyecto.angular.enums.MetodoPagoEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;
    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaPedido;
    @Column(name = "precio_total", nullable = false)
    private Double precioTotal;



    @Column(name = "numero_personas", nullable = false)
    private Integer numeroPersonas;
    @Column(name = "metodo_pago", nullable = false)
    @Enumerated(EnumType.STRING)
    private MetodoPagoEnum metodoPago;
    @Column
    private Double descuento;

    @Enumerated(EnumType.STRING)
    private EstadoPedidoEnum estado = EstadoPedidoEnum.PENDIENTE;


    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    // Multidestino
    @OneToMany(mappedBy = "pedido")
    private List<Viaje> viajes;
}
