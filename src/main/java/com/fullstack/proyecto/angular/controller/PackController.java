package com.fullstack.proyecto.angular.controller;

import com.fullstack.proyecto.angular.dto.PackResponseDTO;
import com.fullstack.proyecto.angular.model.Pack;
import com.fullstack.proyecto.angular.service.PackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/packs")
@CrossOrigin(origins = "http://localhost:4200")
public class PackController {

    private final PackService packService;

    @Autowired
    public PackController(PackService packService) {
        this.packService = packService;
    }

    // =========================
    // CLIENTE — ver packs activos
    // =========================
    @GetMapping
    public ResponseEntity<List<PackResponseDTO>> getPacksActivos() {
        return ResponseEntity.ok(packService.getPacksActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackResponseDTO> getPackById(@PathVariable Long id) {
        return ResponseEntity.ok(packService.getPackById(id));
    }

    // =========================
    // ADMIN
    // =========================
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PackResponseDTO> crearPack(@RequestBody Pack pack) {
        return ResponseEntity.status(HttpStatus.CREATED).body(packService.savePack(pack));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/todos")
    public ResponseEntity<List<PackResponseDTO>> getAllPacks() {
        return ResponseEntity.ok(packService.getAllPacks());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<PackResponseDTO> actualizarPack(
            @RequestBody Pack pack,
            @PathVariable Long id) {
        return ResponseEntity.ok(packService.updatePack(pack, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{idPack}/viajes/{idViaje}")
    public ResponseEntity<PackResponseDTO> addViajeAPack(
            @PathVariable Long idPack,
            @PathVariable Long idViaje) {
        return ResponseEntity.ok(packService.addViajeAPack(idPack, idViaje));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idPack}/viajes/{idViaje}")
    public ResponseEntity<PackResponseDTO> removeViajeFromPack(
            @PathVariable Long idPack,
            @PathVariable Long idViaje) {
        return ResponseEntity.ok(packService.removeViajeFromPack(idPack, idViaje));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePack(@PathVariable Long id) {
        packService.deletePack(id);
        return ResponseEntity.noContent().build();
    }
}