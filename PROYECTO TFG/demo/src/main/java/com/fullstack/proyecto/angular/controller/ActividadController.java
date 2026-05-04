package com.fullstack.proyecto.angular.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.proyecto.angular.dto.ActividadRequestDTO;
import com.fullstack.proyecto.angular.model.Actividad;
import com.fullstack.proyecto.angular.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/actividades")
@CrossOrigin(origins = "http://localhost:4200") // Para que tu Angular no dé error de CORS
public class ActividadController {
    private final ActividadService actividadService;
    @Autowired
    public ActividadController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }


    //CREAR
    @PostMapping
    public Actividad crearActividad(@RequestBody Actividad actividad) {
        return actividadService.saveActividad(actividad);
    }


    //LISTAR
    @GetMapping
    public List<ActividadRequestDTO> listarActividades() {
        return actividadService.getAllActividades();
    }


    //ACTUALZAR
    @PutMapping("/{id}")
    public Actividad actualizarActividad(@RequestBody Actividad actividad, @PathVariable("id") Long id) {
        return actividadService.updateActividad(actividad, id);
    }


    //BORRAR
    @DeleteMapping("/{id}")
    public String eliminarActividad(@PathVariable("id") Long id) {
        return actividadService.deleteByActividadId(id);
    }


    //FILTROS
    @GetMapping("/buscar/nombre")
    public List<ActividadRequestDTO> buscarActividadPorNombre(@RequestParam String nombre) {
        return actividadService.getActividadByNombre(nombre);
    }

    @GetMapping("/buscar/tipo")
    public List<ActividadRequestDTO> buscarActividadPorTipo(@RequestParam String tipo) {
        return actividadService.getActividadByTipo(tipo);
    }
}
