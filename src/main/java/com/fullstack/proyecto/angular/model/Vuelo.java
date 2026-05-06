    package com.fullstack.proyecto.angular.model;

    import com.fullstack.proyecto.angular.enums.ClaseEnum;
    import jakarta.persistence.*;
    import lombok.Data;

    import java.time.LocalDateTime;

    @Entity
    @Table(name = "vuelo")
    @Data
    public class Vuelo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id_vuelo;
        @Column(nullable = false)
        private String aerolinea;
        @Column(name="numero_vuelo",nullable = false, unique = true)
        private String numVuelo;
        @Column(name = "fechaSalida", nullable = false)
        private LocalDateTime fechaSalida;
        @Column(name = "fecha_llegada", nullable = false)
        private LocalDateTime fechaLlegada;
        @Column(nullable = false)
        private String origen;
        @Column(nullable = false)
        private String destino;
        @Enumerated(EnumType.STRING)
        private ClaseEnum clase = ClaseEnum.ECONOMICA;
        @Column(name = "precio_vuelo", nullable = false)
        private Double precioVuelo;

    }
