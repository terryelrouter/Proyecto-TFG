package com.fullstack.proyecto.angular.service;

import com.fullstack.proyecto.angular.dto.HospedajeRequestDTO;
import com.fullstack.proyecto.angular.model.Hospedaje;
import com.fullstack.proyecto.angular.repository.HospedajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HospedajeService {

    private final HospedajeRepository hospedajeRepository;

    @Autowired
    public HospedajeService(HospedajeRepository hospedajeRepository) {
        this.hospedajeRepository = hospedajeRepository;
    }

    private HospedajeRequestDTO mapToDTO(Hospedaje hospedaje) {
        return HospedajeRequestDTO.builder()
                .idHospedaje(hospedaje.getId_hospedaje())
                .nombre(hospedaje.getNombre())
                .direccion(hospedaje.getDireccion())
                .precio_noche(hospedaje.getPrecioNoche())
                .tipo(hospedaje.getTipo())
                .build();
    }

    @Transactional
    public Hospedaje saveHospedaje(Hospedaje hospedaje) {
        return hospedajeRepository.save(hospedaje);
    }

    @Transactional(readOnly = true)
    public List<HospedajeRequestDTO> getAllHospedajes() {
        return hospedajeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Hospedaje updateHospedaje(Hospedaje hospedaje, Long id) {
        Hospedaje hospedajeExistente = hospedajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospedaje no encontrado con id: " + id));

        if (Objects.nonNull(hospedaje.getNombre()) && !"".equalsIgnoreCase(hospedaje.getNombre())) {
            hospedajeExistente.setNombre(hospedaje.getNombre());
        }
        if (Objects.nonNull(hospedaje.getDireccion()) && !"".equalsIgnoreCase(hospedaje.getDireccion())) {
            hospedajeExistente.setDireccion(hospedaje.getDireccion());
        }
        if (Objects.nonNull(hospedaje.getPrecioNoche()) && hospedaje.getPrecioNoche() > 0) {
            hospedajeExistente.setPrecioNoche(hospedaje.getPrecioNoche());
        }
        if (Objects.nonNull(hospedaje.getTipo()) && !"".equalsIgnoreCase(hospedaje.getTipo())) {
            hospedajeExistente.setTipo(hospedaje.getTipo());
        }

        return hospedajeRepository.save(hospedajeExistente);
    }

    @Transactional
    public void deleteHospedajeById(Long id) {
        if (!hospedajeRepository.existsById(id)) {
            throw new RuntimeException("Hospedaje no encontrado con id: " + id);
        }
        hospedajeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<HospedajeRequestDTO> getHospedajesByNombre(String nombre) {
        return hospedajeRepository.findByNombreContaining(nombre).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HospedajeRequestDTO> getHospedajesByTipo(String tipo) {
        return hospedajeRepository.findByTipo(tipo).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HospedajeRequestDTO> getByPrecioNocheBetween(Double min, Double max) {
        return hospedajeRepository.findByPrecioNocheBetween(min, max).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}