package com.fullstack.proyecto.angular.controller;

import com.fullstack.proyecto.angular.dto.ViajeResponseDTO;
import com.fullstack.proyecto.angular.model.Viaje;
import com.fullstack.proyecto.angular.service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/viajes")
@CrossOrigin(origins = "http://localhost:4200")
public class ViajeController {

    private final ViajeService viajeService;

    @Autowired
    public ViajeController(ViajeService viajeService) {
        this.viajeService = viajeService;
    }

    // =========================
    // ADMIN
    // =========================
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ViajeResponseDTO> crearViaje(
            @RequestBody Viaje viaje,
            @RequestParam Long idPedido,
            @RequestParam Long idVuelo,
            @RequestParam(required = false) Long idHospedaje,
            @RequestParam(required = false) Long idActividad) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(viajeService.saveViaje(viaje, idPedido, idVuelo, idHospedaje, idActividad));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ViajeResponseDTO>> listarTodos() {
        return ResponseEntity.ok(viajeService.getAllViajes());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<ViajeResponseDTO> actualizarViaje(
            @RequestBody Viaje viaje,
            @PathVariable Long id) {
        return ResponseEntity.ok(viajeService.updateViaje(viaje, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarViaje(@PathVariable Long id) {
        viajeService.deleteViaje(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // CLIENTE
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<ViajeResponseDTO> getViajeById(@PathVariable Long id) {
        return ResponseEntity.ok(viajeService.getViajeById(id));
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<ViajeResponseDTO>> getViajesByPedido(@PathVariable Long idPedido) {
        return ResponseEntity.ok(viajeService.getViajesByPedido(idPedido));
    }

    @GetMapping("/buscar/origen")
    public ResponseEntity<List<ViajeResponseDTO>> getViajesByOrigen(@RequestParam String origen) {
        return ResponseEntity.ok(viajeService.getViajesByOrigen(origen));
    }

    @GetMapping("/buscar/fechas")
    public ResponseEntity<List<ViajeResponseDTO>> getViajesByFechas(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fin) {
        return ResponseEntity.ok(viajeService.getViajesByFechas(inicio, fin));
    }
}