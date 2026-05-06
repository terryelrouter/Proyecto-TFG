package com.fullstack.proyecto.angular.controller;

import com.fullstack.proyecto.angular.dto.*;
import com.fullstack.proyecto.angular.enums.RolEnum;
import com.fullstack.proyecto.angular.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // =========================
    // RUTAS ESTÁTICAS PRIMERO
    // =========================
    @PostMapping("/registro")
    public ResponseEntity<ClienteResponseDTO> registrar(@RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.registrarCliente(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO dto) {
        return ResponseEntity.ok(clienteService.loginCliente(dto));
    }

    @GetMapping("/perfil")
    public ResponseEntity<ClienteResponseDTO> getPerfil(@RequestParam String email) {
        return ResponseEntity.ok(clienteService.getPerfilCliente(email));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> getAll() {
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    // =========================
    // RUTAS CON {id} DESPUÉS
    // =========================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> updateCliente(
            @RequestBody ClienteUpdateDTO dto,
            @PathVariable Long id) {
        return ResponseEntity.ok(clienteService.updateCliente(dto, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/rol")
    public ResponseEntity<ClienteResponseDTO> cambiarRol(
            @PathVariable Long id,
            @RequestParam RolEnum rol) {
        return ResponseEntity.ok(clienteService.cambiarRol(id, rol));
    }
}