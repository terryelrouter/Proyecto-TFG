package com.fullstack.proyecto.angular.service;

import com.fullstack.proyecto.angular.dto.VueloRequestDTO;
import com.fullstack.proyecto.angular.enums.ClaseEnum;
import com.fullstack.proyecto.angular.model.Vuelo;
import com.fullstack.proyecto.angular.repository.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VueloService {

    private final VueloRepository vueloRepository;

    @Autowired
    public VueloService(VueloRepository vueloRepository) {
        this.vueloRepository = vueloRepository;
    }

    private VueloRequestDTO mapToRequestDTO(Vuelo vuelo) {
        return VueloRequestDTO.builder()
                .idVuelo(vuelo.getId_vuelo())
                .aerolinea(vuelo.getAerolinea())
                .num_vuelo(vuelo.getNumVuelo())
                .origen(vuelo.getOrigen())
                .destino(vuelo.getDestino())
                .fecha_llegada(vuelo.getFechaLlegada())
                .fecha_salida(vuelo.getFechaSalida())
                .precio_vuelo(vuelo.getPrecioVuelo())
                .clase(vuelo.getClase())
                .build();
    }

    @Transactional
    public Vuelo saveVuelo(Vuelo vuelo) {
        return vueloRepository.save(vuelo);
    }

    @Transactional(readOnly = true)
    public List<VueloRequestDTO> getAllVuelos() {
        return vueloRepository.findAll().stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Vuelo updateVuelo(Vuelo datosNuevos, Long id) {
        Vuelo vueloExistente = vueloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado con id: " + id));

        if (Objects.nonNull(datosNuevos.getAerolinea()) && !datosNuevos.getAerolinea().isEmpty()) {
            vueloExistente.setAerolinea(datosNuevos.getAerolinea());
        }
        if (Objects.nonNull(datosNuevos.getNumVuelo()) && !datosNuevos.getNumVuelo().isEmpty()) {
            vueloExistente.setNumVuelo(datosNuevos.getNumVuelo());
        }
        if (Objects.nonNull(datosNuevos.getPrecioVuelo()) && datosNuevos.getPrecioVuelo() > 0) {
            vueloExistente.setPrecioVuelo(datosNuevos.getPrecioVuelo());
        }
        if (Objects.nonNull(datosNuevos.getOrigen()) && !datosNuevos.getOrigen().isEmpty()) {
            vueloExistente.setOrigen(datosNuevos.getOrigen());
        }
        if (Objects.nonNull(datosNuevos.getDestino()) && !datosNuevos.getDestino().isEmpty()) {
            vueloExistente.setDestino(datosNuevos.getDestino());
        }
        if (Objects.nonNull(datosNuevos.getFechaSalida())) {
            vueloExistente.setFechaSalida(datosNuevos.getFechaSalida());
        }
        if (Objects.nonNull(datosNuevos.getFechaLlegada())) {
            vueloExistente.setFechaLlegada(datosNuevos.getFechaLlegada());
        }
        if (Objects.nonNull(datosNuevos.getClase())) {
            vueloExistente.setClase(datosNuevos.getClase());
        }

        return vueloRepository.save(vueloExistente);
    }

    @Transactional
    public void deleteVueloById(Long id) {
        if (!vueloRepository.existsById(id)) {
            throw new RuntimeException("Vuelo no encontrado con id: " + id);
        }
        vueloRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<VueloRequestDTO> getVuelosPorRuta(String origen, String destino) {
        return vueloRepository.findByOrigenAndDestino(origen, destino).stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VueloRequestDTO> getVuelosPorPrecio(Double min, Double max) {
        return vueloRepository.findByPrecioVueloBetween(min, max).stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VueloRequestDTO> getVuelosPorClase(ClaseEnum clase) {
        return vueloRepository.findByClase(clase).stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VueloRequestDTO> getVuelosPorNumero(String num_vuelo) {
        return vueloRepository.findByNumVuelo(num_vuelo).stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }
}