package com.fullstack.proyecto.angular.controller;

import com.fullstack.proyecto.angular.dto.ActividadRequestDTO;
import com.fullstack.proyecto.angular.model.Actividad;
import com.fullstack.proyecto.angular.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/actividades")
@CrossOrigin(origins = "http://localhost:4200")
public class ActividadController {

    private final ActividadService actividadService;

    @Autowired
    public ActividadController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }

    @PostMapping
    public ResponseEntity<Actividad> crearActividad(@RequestBody Actividad actividad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(actividadService.saveActividad(actividad));
    }

    @GetMapping
    public ResponseEntity<List<ActividadRequestDTO>> listarActividades() {
        return ResponseEntity.ok(actividadService.getAllActividades());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Actividad> actualizarActividad(
            @RequestBody Actividad actividad,
            @PathVariable Long id) {
        return ResponseEntity.ok(actividadService.updateActividad(actividad, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Long id) {
        actividadService.deleteByActividadId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<ActividadRequestDTO>> buscarActividadPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(actividadService.getActividadByNombre(nombre));
    }

    @GetMapping("/buscar/tipo")
    public ResponseEntity<List<ActividadRequestDTO>> buscarActividadPorTipo(@RequestParam String tipo) {
        return ResponseEntity.ok(actividadService.getActividadByTipo(tipo));
    }

    @GetMapping("/buscar/precio")
    public ResponseEntity<List<ActividadRequestDTO>> buscarActividadPorPrecio(
            @RequestParam Double min,
            @RequestParam Double max) {
        return ResponseEntity.ok(actividadService.getPrecioActividadBetween(min, max));
    }
}