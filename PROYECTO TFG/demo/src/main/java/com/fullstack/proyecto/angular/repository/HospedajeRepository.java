package com.fullstack.proyecto.angular.repository;


import com.fullstack.proyecto.angular.model.Hospedaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospedajeRepository extends JpaRepository<Hospedaje,Long> {
    List<Hospedaje> findByTipo(String tipo);
    List<Hospedaje> findByNombreContaining(String nombre);
    List<Hospedaje> findByPrecioNocheBetween(Double precioMin, Double precioMax);
}
