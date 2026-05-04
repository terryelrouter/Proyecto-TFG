package com.fullstack.proyecto.angular.controller;

import com.fullstack.proyecto.angular.dto.VueloRequestDTO;
import com.fullstack.proyecto.angular.model.Vuelo;
import com.fullstack.proyecto.angular.service.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vuelos")
@CrossOrigin(origins = "http://localhost:4200") // Conexión directa con tu Angular CORS
public class VueloController {

    private final VueloService vueloService;

    @Autowired
    public VueloController(VueloService vueloService) {
        this.vueloService = vueloService;
    }

    // --- OPERACIONES CRUD

    @PostMapping
    public Vuelo crearVuelo(@RequestBody Vuelo vuelo) {
        return vueloService.saveVuelo(vuelo);
    }

    @GetMapping
    public List<VueloRequestDTO> listarTodos() {
        return vueloService.getAllVuelos();
    }

    @PutMapping("/{id}")
    public Vuelo actualizarVuelo(@RequestBody Vuelo vuelo, @PathVariable("id") Long id) {
        return vueloService.updateVuelo(vuelo, id);
    }

    @DeleteMapping("/{id}")
    public String eliminarVuelo(@PathVariable("id") Long id) {
        return vueloService.deleteVueloById(id);
    }

    // FILTROS
    @GetMapping("/buscar/ruta")
    public List<VueloRequestDTO> buscarVueloPorRuta(
            @RequestParam String origen,
            @RequestParam String destino) {
        return vueloService.getVuelosPorRuta(origen, destino);
    }

    // Buscar por presupuesto
    @GetMapping("/buscar/precio")
    public List<VueloRequestDTO> buscarVueloPorPrecio(
            @RequestParam Double min,
            @RequestParam Double max) {
        return vueloService.getVuelosPorPrecio(min, max);
    }

    // Buscar por tipo de clase
    @GetMapping("/buscar/clase")
    public List<VueloRequestDTO> buscarVueloPorClase(@RequestParam String clase) {
        return vueloService.getVuelosPorClase(clase);
    }

    // Buscar por tipo de clase
    @GetMapping("/buscar/{num_vuelo}")
    public List<VueloRequestDTO> buscarVuelosPorNumeroVuelo(@PathVariable("num_vuelo")@RequestParam  String num_vuelo) {
        return vueloService.getVuelosPorNumero(num_vuelo);
    }
}
