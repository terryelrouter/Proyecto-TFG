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

    public HospedajeRequestDTO mapToDTO(Hospedaje hospedaje) {
        return HospedajeRequestDTO.builder()
                .idHospedaje(hospedaje.getId_hospedaje())
                .nombre(hospedaje.getNombre())
                .direccion(hospedaje.getDireccion())
                .precio_noche(hospedaje.getPrecioNoche())
                .tipo(hospedaje.getTipo())
                .build();
    }




    // CREATE
    public Hospedaje saveHospedaje(Hospedaje hospedaje) {
        return hospedajeRepository.save(hospedaje);
    }

    //Read
    @Transactional(readOnly = true)
    public List<HospedajeRequestDTO> getAllHospedajes() {
        return hospedajeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }



    // UPDATE
    public Hospedaje updateHospedaje(Hospedaje hospedaje, Long id) {
        Hospedaje hospedaje1 = hospedajeRepository.findById(id).get();

        if (Objects.nonNull(hospedaje.getNombre()) && !"".equalsIgnoreCase(hospedaje.getNombre())) {
            hospedaje1.setNombre(hospedaje.getNombre());
        }
        if (Objects.nonNull(hospedaje.getDireccion()) && !"".equalsIgnoreCase(hospedaje.getDireccion())) {
            hospedaje1.setDireccion(hospedaje.getDireccion());
        }
        if (Objects.nonNull(hospedaje.getPrecioNoche())) {
            hospedaje1.setPrecioNoche(hospedaje.getPrecioNoche());
        }
        if (Objects.nonNull(hospedaje.getTipo()) && !"".equalsIgnoreCase(hospedaje.getTipo())) {
            hospedaje1.setTipo(hospedaje.getTipo());
        }

        return hospedajeRepository.save(hospedaje1);
    }

    // DELETE
    public String deleteHospedajeById(Long id) {
        if (hospedajeRepository.existsById(id)) {
            hospedajeRepository.deleteById(id);
            return "Eliminado";
        }
        return "No existe";
    }

    //Buscar Por Nombre
    public List<HospedajeRequestDTO> getHospedajesByNombre(String nombre) {
        List<Hospedaje> hospedajes= hospedajeRepository.findByNombreContaining(nombre);
                return hospedajes.stream()
        .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    //Buscar Por Tipo
    public List<HospedajeRequestDTO> getHospedajesByTipo(String tipo) {
        List<Hospedaje> hospedajes= hospedajeRepository.findByTipo(tipo);
        return hospedajes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    // Buscar entre precios
    public List<HospedajeRequestDTO> getByPrecioNocheBetween(Double min, Double max) {
        return hospedajeRepository.findByPrecioNocheBetween(min, max).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

}
