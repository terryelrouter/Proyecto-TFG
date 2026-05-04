package com.fullstack.proyecto.angular.controller;

import com.fullstack.proyecto.angular.dto.HospedajeRequestDTO;
import com.fullstack.proyecto.angular.model.Hospedaje;
import com.fullstack.proyecto.angular.service.HospedajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/hospedajes")
@CrossOrigin(origins = "http://localhost:4200") // Permite la conexión con Angular CORS
public class HospedajeController {

    private final HospedajeService hospedajeService;

    @Autowired
    public HospedajeController(HospedajeService hospedajeService) {
        this.hospedajeService = hospedajeService;
    }

    // 1. CREAR
    @PostMapping
    public Hospedaje crearHospedaje(@RequestBody Hospedaje hospedaje) {
        return hospedajeService.saveHospedaje(hospedaje);
    }

    // LISTAR
    @GetMapping
    public List<HospedajeRequestDTO> listarHospedajes() {
        return hospedajeService.getAllHospedajes();
    }

    //ACTUALIZAR
    @PutMapping("/{id}")
    public Hospedaje actualizarHospedaje(@RequestBody Hospedaje hospedaje, @PathVariable("id") Long id) {
        return hospedajeService.updateHospedaje(hospedaje, id);
    }

    //ELIMINAR
    @DeleteMapping("/{id}")
    public String eliminarHospedajePorId(@PathVariable("id") Long id) {
        return hospedajeService.deleteHospedajeById(id);
    }




    //FILTROS

    @GetMapping("/buscar/nombre")
    public List<HospedajeRequestDTO> buscarHospedajePorNombre(@RequestParam String nombre) {
        return hospedajeService.getHospedajesByNombre(nombre);
    }


    @GetMapping("/buscar/tipo")
    public List<HospedajeRequestDTO> buscarHospedajePorTipo(@RequestParam String tipo) {
        return hospedajeService.getHospedajesByTipo(tipo);
    }


    @GetMapping("/buscar/precio")
    public List<HospedajeRequestDTO> buscarHospedajePorPrecioBetween(
            @RequestParam Double min,
            @RequestParam Double max) {
        return hospedajeService.getByPrecioNocheBetween(min, max);
    }
}
