package com.fullstack.proyecto.angular.repository;

import com.fullstack.proyecto.angular.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad,Long> {
    List<Actividad> findByTipo(String tipo);
    List<Actividad> findByNombreContaining(String nombre);
    List<Actividad> findByPrecioActividadBetween(Double precioMin, Double precioMax);

}
