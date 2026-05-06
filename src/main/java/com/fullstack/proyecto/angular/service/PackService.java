package com.fullstack.proyecto.angular.service;

import com.fullstack.proyecto.angular.dto.PackResponseDTO;
import com.fullstack.proyecto.angular.dto.ViajeResponseDTO;
import com.fullstack.proyecto.angular.model.Pack;
import com.fullstack.proyecto.angular.model.Viaje;
import com.fullstack.proyecto.angular.repository.PackRepository;
import com.fullstack.proyecto.angular.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PackService {

    private final PackRepository packRepository;
    private final ViajeRepository viajeRepository;
    private final ViajeService viajeService;

    @Autowired
    public PackService(PackRepository packRepository,
                       ViajeRepository viajeRepository,
                       ViajeService viajeService) {
        this.packRepository = packRepository;
        this.viajeRepository = viajeRepository;
        this.viajeService = viajeService;
    }

    private PackResponseDTO mapToDTO(Pack pack) {
        List<ViajeResponseDTO> viajes = pack.getViajes().stream()
                .map(viajeService::mapToDTO)
                .collect(Collectors.toList());

        return PackResponseDTO.builder()
                .idPack(pack.getIdPack())
                .nombre(pack.getNombre())
                .descripcion(pack.getDescripcion())
                .precioPack(pack.getPrecioPack())
                .descuento(pack.getDescuento())
                .activo(pack.getActivo())
                .imagenUrl(pack.getImagenUrl())
                .viajes(viajes)
                .build();
    }

    @Transactional
    public PackResponseDTO savePack(Pack pack) {
        return mapToDTO(packRepository.save(pack));
    }

    @Transactional(readOnly = true)
    public List<PackResponseDTO> getAllPacks() {
        return packRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Solo los activos para el cliente
    @Transactional(readOnly = true)
    public List<PackResponseDTO> getPacksActivos() {
        return packRepository.findByActivoTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PackResponseDTO getPackById(Long id) {
        return packRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Pack no encontrado con id: " + id));
    }

    @Transactional
    public PackResponseDTO updatePack(Pack datosNuevos, Long id) {
        Pack packExistente = packRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pack no encontrado con id: " + id));

        if (Objects.nonNull(datosNuevos.getNombre()) && !datosNuevos.getNombre().isEmpty()) {
            packExistente.setNombre(datosNuevos.getNombre());
        }
        if (Objects.nonNull(datosNuevos.getDescripcion()) && !datosNuevos.getDescripcion().isEmpty()) {
            packExistente.setDescripcion(datosNuevos.getDescripcion());
        }
        if (Objects.nonNull(datosNuevos.getPrecioPack())) {
            packExistente.setPrecioPack(datosNuevos.getPrecioPack());
        }
        if (Objects.nonNull(datosNuevos.getDescuento())) {
            packExistente.setDescuento(datosNuevos.getDescuento());
        }
        if (Objects.nonNull(datosNuevos.getActivo())) {
            packExistente.setActivo(datosNuevos.getActivo());
        }
        if (Objects.nonNull(datosNuevos.getImagenUrl()) && !datosNuevos.getImagenUrl().isEmpty()) {
            packExistente.setImagenUrl(datosNuevos.getImagenUrl());
        }

        return mapToDTO(packRepository.save(packExistente));
    }

    // Añadir un viaje al pack
    @Transactional
    public PackResponseDTO addViajeAPack(Long idPack, Long idViaje) {
        Pack pack = packRepository.findById(idPack)
                .orElseThrow(() -> new RuntimeException("Pack no encontrado con id: " + idPack));
        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado con id: " + idViaje));

        viaje.setPack(pack);
        viajeRepository.save(viaje);
        return mapToDTO(packRepository.findById(idPack).get());
    }

    // Quitar un viaje del pack
    @Transactional
    public PackResponseDTO removeViajeFromPack(Long idPack, Long idViaje) {
        Pack pack = packRepository.findById(idPack)
                .orElseThrow(() -> new RuntimeException("Pack no encontrado con id: " + idPack));
        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado con id: " + idViaje));

        viaje.setPack(null);
        viajeRepository.save(viaje);
        return mapToDTO(packRepository.findById(idPack).get());
    }

    @Transactional
    public void deletePack(Long id) {
        if (!packRepository.existsById(id)) {
            throw new RuntimeException("Pack no encontrado con id: " + id);
        }
        // Desasociar viajes antes de borrar
        Pack pack = packRepository.findById(id).get();
        pack.getViajes().forEach(v -> v.setPack(null));
        viajeRepository.saveAll(pack.getViajes());
        packRepository.deleteById(id);
    }
}