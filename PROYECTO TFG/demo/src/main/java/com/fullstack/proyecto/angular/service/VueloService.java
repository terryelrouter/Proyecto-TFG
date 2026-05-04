package com.fullstack.proyecto.angular.service;

import com.fullstack.proyecto.angular.dto.VueloRequestDTO;
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

    // Mapeador de Model a DTO
    public VueloRequestDTO mapToRequestDTO(Vuelo vuelo) {
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

    // --- OPERACIONES CRUD ---

    // CREATE
    public Vuelo saveVuelo(Vuelo vuelo) {
        return vueloRepository.save(vuelo);
    }

    // READ (Todos en formato DTO)
    @Transactional(readOnly = true)
    public List<VueloRequestDTO> getAllVuelos() {
        return vueloRepository.findAll().stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
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

        if (Objects.nonNull(datosNuevos.getOrigen())) {
            vueloExistente.setOrigen(datosNuevos.getOrigen());
        }

        if (Objects.nonNull(datosNuevos.getDestino())) {
            vueloExistente.setDestino(datosNuevos.getDestino());
        }

        if (Objects.nonNull(datosNuevos.getFechaSalida())) {
            vueloExistente.setFechaSalida(datosNuevos.getFechaSalida());
        }

        // 3. Guardamos el objeto que ya tiene los cambios aplicados
        return vueloRepository.save(vueloExistente);
    }

    // DELETE
    public String deleteVueloById(Long id) {
        if (vueloRepository.existsById(id)) {
            vueloRepository.deleteById(id);
            return "Vuelo eliminado con éxito";
        }
        return "El ID no existe";
    }


    // Buscar por Origen y Destino
    public List<VueloRequestDTO> getVuelosPorRuta(String origen, String destino) {
        return vueloRepository.findByOrigenAndDestino(origen, destino).stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }

    // Buscar por rango de precios
    public List<VueloRequestDTO> getVuelosPorPrecio(Double min, Double max) {
        return vueloRepository.findByPrecioVueloBetween(min, max).stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }

    // Buscar por clase
    public List<VueloRequestDTO> getVuelosPorClase(String clase) {
        return vueloRepository.findByClase(clase).stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }

    // Buscar por clase
    public List<VueloRequestDTO> getVuelosPorNumero(String num_vuelo) {
        return vueloRepository.findByNumVuelo(num_vuelo).stream()
                .map(this::mapToRequestDTO)
                .collect(Collectors.toList());
    }
}
