package com.fullstack.proyecto.angular.repository;

import com.fullstack.proyecto.angular.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    List<Pedido> findByCliente_IdCliente(Long idCliente);
}
