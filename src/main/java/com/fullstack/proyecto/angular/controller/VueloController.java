package com.fullstack.proyecto.angular.controller;

import com.fullstack.proyecto.angular.dto.VueloRequestDTO;
import com.fullstack.proyecto.angular.enums.ClaseEnum;
import com.fullstack.proyecto.angular.model.Vuelo;
import com.fullstack.proyecto.angular.service.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vuelos")
@CrossOrigin(origins = "http://localhost:4200")
public class VueloController {

    private final VueloService vueloService;

    @Autowired
    public VueloController(VueloService vueloService) {
        this.vueloService = vueloService;
    }

    @PostMapping
    public ResponseEntity<Vuelo> crearVuelo(@RequestBody Vuelo vuelo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vueloService.saveVuelo(vuelo));
    }

    @GetMapping
    public ResponseEntity<List<VueloRequestDTO>> listarTodos() {
        return ResponseEntity.ok(vueloService.getAllVuelos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vuelo> actualizarVuelo(
            @RequestBody Vuelo vuelo,
            @PathVariable Long id) {
        return ResponseEntity.ok(vueloService.updateVuelo(vuelo, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVuelo(@PathVariable Long id) {
        vueloService.deleteVueloById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/ruta")
    public ResponseEntity<List<VueloRequestDTO>> buscarVueloPorRuta(
            @RequestParam String origen,
            @RequestParam String destino) {
        return ResponseEntity.ok(vueloService.getVuelosPorRuta(origen, destino));
    }

    @GetMapping("/buscar/precio")
    public ResponseEntity<List<VueloRequestDTO>> buscarVueloPorPrecio(
            @RequestParam Double min,
            @RequestParam Double max) {
        return ResponseEntity.ok(vueloService.getVuelosPorPrecio(min, max));
    }

    @GetMapping("/buscar/clase")
    public ResponseEntity<List<VueloRequestDTO>> buscarVueloPorClase(@RequestParam ClaseEnum clase) {
        return ResponseEntity.ok(vueloService.getVuelosPorClase(clase));
    }

    @GetMapping("/buscar/{num_vuelo}")
    public ResponseEntity<List<VueloRequestDTO>> buscarVuelosPorNumeroVuelo(
            @PathVariable String num_vuelo) {
        return ResponseEntity.ok(vueloService.getVuelosPorNumero(num_vuelo));
    }
}