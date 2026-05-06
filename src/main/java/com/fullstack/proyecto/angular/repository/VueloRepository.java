package com.fullstack.proyecto.angular.repository;


import com.fullstack.proyecto.angular.enums.ClaseEnum;
import com.fullstack.proyecto.angular.model.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo,Long> {
    List<Vuelo> findByNumVuelo(String num_vuelo);
    List<Vuelo> findByClase(ClaseEnum clase);
    List<Vuelo> findByOrigenAndDestino(String origen,String destino);
    List<Vuelo> findByPrecioVueloBetween(Double precioMin, Double precioMax);
}
