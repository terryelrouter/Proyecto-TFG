package com.fullstack.proyecto.angular.controller;

import com.fullstack.proyecto.angular.dto.HospedajeRequestDTO;
import com.fullstack.proyecto.angular.model.Hospedaje;
import com.fullstack.proyecto.angular.service.HospedajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/hospedajes")
@CrossOrigin(origins = "http://localhost:4200")
public class HospedajeController {

    private final HospedajeService hospedajeService;

    @Autowired
    public HospedajeController(HospedajeService hospedajeService) {
        this.hospedajeService = hospedajeService;
    }

    @PostMapping
    public ResponseEntity<Hospedaje> crearHospedaje(@RequestBody Hospedaje hospedaje) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hospedajeService.saveHospedaje(hospedaje));
    }

    @GetMapping
    public ResponseEntity<List<HospedajeRequestDTO>> listarHospedajes() {
        return ResponseEntity.ok(hospedajeService.getAllHospedajes());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Hospedaje> actualizarHospedaje(
            @RequestBody Hospedaje hospedaje,
            @PathVariable Long id) {
        return ResponseEntity.ok(hospedajeService.updateHospedaje(hospedaje, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHospedajePorId(@PathVariable Long id) {
        hospedajeService.deleteHospedajeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<HospedajeRequestDTO>> buscarHospedajePorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(hospedajeService.getHospedajesByNombre(nombre));
    }

    @GetMapping("/buscar/tipo")
    public ResponseEntity<List<HospedajeRequestDTO>> buscarHospedajePorTipo(@RequestParam String tipo) {
        return ResponseEntity.ok(hospedajeService.getHospedajesByTipo(tipo));
    }

    @GetMapping("/buscar/precio")
    public ResponseEntity<List<HospedajeRequestDTO>> buscarHospedajePorPrecioBetween(
            @RequestParam Double min,
            @RequestParam Double max) {
        return ResponseEntity.ok(hospedajeService.getByPrecioNocheBetween(min, max));
    }
}