package com.fullstack.proyecto.angular.repository;

import com.fullstack.proyecto.angular.model.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackRepository extends JpaRepository<Pack, Long> {
    List<Pack> findByActivoTrue();
}