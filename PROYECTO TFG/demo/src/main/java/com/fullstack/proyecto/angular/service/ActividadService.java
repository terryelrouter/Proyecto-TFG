package com.fullstack.proyecto.angular.service;

import com.fullstack.proyecto.angular.dto.ActividadRequestDTO;
import com.fullstack.proyecto.angular.dto.HospedajeRequestDTO;
import com.fullstack.proyecto.angular.model.Actividad;
import com.fullstack.proyecto.angular.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ActividadService {
    private final ActividadRepository actividadRepository;

    @Autowired
    public ActividadService(ActividadRepository actividadRepository){
        this.actividadRepository=actividadRepository;
    }

    public ActividadRequestDTO mapToRequestDTO(Actividad actividad) {
        return ActividadRequestDTO.builder()
                .idActividad(actividad.getIdActividad())
                .nombre(actividad.getNombre())
                .descripcion(actividad.getDescripcion())
                .tipo(actividad.getTipo())
                .precio_actividad(actividad.getPrecioActividad())
                .build();
    }


    //Update
    public Actividad
    updateActividad(Actividad actividad,
                  Long id_actividad) {

        Actividad actividad1
                = actividadRepository.findById(id_actividad)
                .get();
        if (Objects.nonNull(actividad.getNombre()) && !"".equalsIgnoreCase(actividad.getNombre())) {
            actividad1.setNombre(actividad.getNombre());
        }
        if (Objects.nonNull(actividad.getDescripcion()) && !"".equalsIgnoreCase(actividad.getDescripcion())) {
            actividad1.setDescripcion(actividad.getDescripcion());
        }
        if (Objects.nonNull(actividad.getTipo()) && !"".equalsIgnoreCase(actividad.getTipo())) {
            actividad1.setTipo(actividad.getTipo());
        }
        return actividadRepository.save(actividad1);
    }

    // operation CREATE
    public Actividad saveActividad(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    // Read operation
    @Transactional(readOnly = true)
    public List<ActividadRequestDTO> getAllActividades() {
        List<Actividad> actividades = actividadRepository.findAll();
        return actividades.stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }

    // Delete operation
    public String deleteByActividadId(Long id_actividad) {
        String msg = "";
        if (actividadRepository.existsById(id_actividad)) {
            actividadRepository.deleteById(id_actividad);
            msg = "Deleted Successfully";
        } else {
            msg = "Id not found";
        }
        return msg;
    }



    //Obtener Actividades por nombre
    @Transactional(readOnly = true)
    public List<ActividadRequestDTO> getActividadByNombre(String nombre){
        List<Actividad> actividades = actividadRepository.findByNombreContaining(nombre);
        return actividades.stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }

    //Obtener Actividades por tipo
    @Transactional(readOnly = true)
    public List<ActividadRequestDTO> getActividadByTipo(String tipo){
        List<Actividad> actividades = actividadRepository.findByTipo(tipo);
        return actividades.stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }

    // Buscar entre precios
    public List<ActividadRequestDTO> getPrecioActividadBetween(Double min, Double max) {
        return actividadRepository.findByPrecioActividadBetween(min, max).stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }



}
