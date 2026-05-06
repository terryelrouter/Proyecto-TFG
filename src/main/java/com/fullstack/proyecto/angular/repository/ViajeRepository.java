package com.fullstack.proyecto.angular.repository;

import com.fullstack.proyecto.angular.model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje,Long> {
    List<Viaje> findByPedido_IdPedido(Long idPedido);
    List<Viaje> findByOrigenContaining(String origen);
    List<Viaje> findByFechaInicioBetween(LocalDate inicio, LocalDate fin);
}
