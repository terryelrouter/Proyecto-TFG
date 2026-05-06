package com.fullstack.proyecto.angular.controller;
import com.fullstack.proyecto.angular.dto.PedidoResponseDTO;
import com.fullstack.proyecto.angular.enums.EstadoPedidoEnum;
import com.fullstack.proyecto.angular.model.Pedido;
import com.fullstack.proyecto.angular.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService ps;

    @Autowired
    public PedidoController(PedidoService ps) {
        this.ps = ps;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> createPedido(
            @RequestBody Pedido pedido,
            @RequestParam Long idCliente) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ps.createPedido(pedido, idCliente));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> getAllPedidos() {
        return ResponseEntity.ok(ps.getAllPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> getPedidoById(@PathVariable Long id) {
        return ResponseEntity.ok(ps.getPedidoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> updatePedido(
            @PathVariable Long id,
            @RequestBody Pedido pedido) {
        return ResponseEntity.ok(ps.updatePedido(id, pedido));
    }


        @PatchMapping("/{id}/estado")
        public ResponseEntity<PedidoResponseDTO> updateEstado (
                @PathVariable Long id,
                @RequestParam EstadoPedidoEnum nuevoEstado){
            return ResponseEntity.ok(ps.updateEstado(id, nuevoEstado));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePedido (@PathVariable Long id){
            ps.deletePedido(id);
            return ResponseEntity.noContent().build();
        }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<PedidoResponseDTO>> getPedidosByCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(ps.getPedidosByCliente(idCliente));
    }
    }
