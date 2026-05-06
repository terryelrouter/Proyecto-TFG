package com.fullstack.proyecto.angular.service;

import com.fullstack.proyecto.angular.dto.ActividadRequestDTO;
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
    public ActividadService(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    private ActividadRequestDTO mapToRequestDTO(Actividad actividad) {
        return ActividadRequestDTO.builder()
                .idActividad(actividad.getIdActividad())
                .nombre(actividad.getNombre())
                .descripcion(actividad.getDescripcion())
                .tipo(actividad.getTipo())
                .precio_actividad(actividad.getPrecioActividad())
                .build();
    }

    @Transactional
    public Actividad saveActividad(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    @Transactional(readOnly = true)
    public List<ActividadRequestDTO> getAllActividades() {
        return actividadRepository.findAll().stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Actividad updateActividad(Actividad actividad, Long id) {
        Actividad actividadExistente = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada con id: " + id));

        if (Objects.nonNull(actividad.getNombre()) && !"".equalsIgnoreCase(actividad.getNombre())) {
            actividadExistente.setNombre(actividad.getNombre());
        }
        if (Objects.nonNull(actividad.getDescripcion()) && !"".equalsIgnoreCase(actividad.getDescripcion())) {
            actividadExistente.setDescripcion(actividad.getDescripcion());
        }
        if (Objects.nonNull(actividad.getTipo()) && !"".equalsIgnoreCase(actividad.getTipo())) {
            actividadExistente.setTipo(actividad.getTipo());
        }
        if (Objects.nonNull(actividad.getPrecioActividad()) && actividad.getPrecioActividad() >= 0) {
            actividadExistente.setPrecioActividad(actividad.getPrecioActividad());
        }

        return actividadRepository.save(actividadExistente);
    }

    @Transactional
    public void deleteByActividadId(Long id) {
        if (!actividadRepository.existsById(id)) {
            throw new RuntimeException("Actividad no encontrada con id: " + id);
        }
        actividadRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ActividadRequestDTO> getActividadByNombre(String nombre) {
        return actividadRepository.findByNombreContaining(nombre).stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ActividadRequestDTO> getActividadByTipo(String tipo) {
        return actividadRepository.findByTipo(tipo).stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ActividadRequestDTO> getPrecioActividadBetween(Double min, Double max) {
        return actividadRepository.findByPrecioActividadBetween(min, max).stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }
}