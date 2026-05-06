package com.fullstack.proyecto.angular.service;

import com.fullstack.proyecto.angular.dto.ViajeResponseDTO;
import com.fullstack.proyecto.angular.model.*;
import com.fullstack.proyecto.angular.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ViajeService {

    private final ViajeRepository vr;
    private final PedidoRepository pr;
    private final VueloRepository vueloRepository;
    private final HospedajeRepository hr;
    private final ActividadRepository ar;

    @Autowired
    public ViajeService(ViajeRepository vr, PedidoRepository pr,
                        VueloRepository vueloRepository,
                        HospedajeRepository hr, ActividadRepository ar) {
        this.vr = vr;
        this.pr = pr;
        this.vueloRepository = vueloRepository;
        this.hr = hr;
        this.ar = ar;
    }

    public ViajeResponseDTO mapToDTO(Viaje viaje) {
        ViajeResponseDTO.ViajeResponseDTOBuilder builder = ViajeResponseDTO.builder()
                .idViaje(viaje.getId_viaje())
                .origen(viaje.getOrigen())
                .fechaInicio(viaje.getFechaInicio())
                .fechaFin(viaje.getFechaFin())
                .precio(viaje.getPrecio())
                .descripcion(viaje.getDescripcion())
                .tiempoAtm(viaje.getTiempoAtm())
                .distanciaEntreVuelos(viaje.getDistanciaEntreVuelos());

        if (Objects.nonNull(viaje.getPedido())) {
            builder.idPedido(viaje.getPedido().getIdPedido());
        }
        if (Objects.nonNull(viaje.getVuelo())) {
            builder.aerolinea(viaje.getVuelo().getAerolinea())
                    .origenVuelo(viaje.getVuelo().getOrigen())
                    .destinoVuelo(viaje.getVuelo().getDestino());
        }
        if (Objects.nonNull(viaje.getHospedaje())) {
            builder.nombreHospedaje(viaje.getHospedaje().getNombre());
        }
        if (Objects.nonNull(viaje.getActividad())) {
            builder.nombreActividad(viaje.getActividad().getNombre());
        }

        return builder.build();
    }

    public Double calcularPrecioViaje(Viaje viaje) {
        double precioVuelo = 0.0;
        double precioHospedaje = 0.0;
        double precioActividad = 0.0;

        if (Objects.nonNull(viaje.getVuelo())) {
            precioVuelo = viaje.getVuelo().getPrecioVuelo();
        }
        if (Objects.nonNull(viaje.getHospedaje()) &&
                Objects.nonNull(viaje.getFechaInicio()) &&
                Objects.nonNull(viaje.getFechaFin())) {
            long noches = ChronoUnit.DAYS.between(viaje.getFechaInicio(), viaje.getFechaFin());
            precioHospedaje = viaje.getHospedaje().getPrecioNoche() * noches;
        }
        if (Objects.nonNull(viaje.getActividad())) {
            precioActividad = viaje.getActividad().getPrecioActividad();
        }

        return precioVuelo + precioHospedaje + precioActividad;
    }

    @Transactional
    public ViajeResponseDTO saveViaje(Viaje viaje, Long idPedido, Long idVuelo,
                                      Long idHospedaje, Long idActividad) {
        Pedido pedido = pr.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + idPedido));
        Vuelo vuelo = vueloRepository.findById(idVuelo)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado con id: " + idVuelo));

        viaje.setPedido(pedido);
        viaje.setVuelo(vuelo);

        // Hospedaje y actividad son opcionales en el viaje de vuelta
        if (Objects.nonNull(idHospedaje)) {
            Hospedaje hospedaje = hr.findById(idHospedaje)
                    .orElseThrow(() -> new RuntimeException("Hospedaje no encontrado con id: " + idHospedaje));
            viaje.setHospedaje(hospedaje);
        }
        if (Objects.nonNull(idActividad)) {
            Actividad actividad = ar.findById(idActividad)
                    .orElseThrow(() -> new RuntimeException("Actividad no encontrada con id: " + idActividad));
            viaje.setActividad(actividad);
        }

        // Calcular precio automáticamente
        viaje.setPrecio(calcularPrecioViaje(viaje));

        return mapToDTO(vr.save(viaje));
    }

    @Transactional(readOnly = true)
    public List<ViajeResponseDTO> getAllViajes() {
        return vr.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ViajeResponseDTO getViajeById(Long id) {
        return vr.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado con id: " + id));
    }

    @Transactional
    public ViajeResponseDTO updateViaje(Viaje datosNuevos, Long id) {
        Viaje viajeExistente = vr.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado con id: " + id));

        if (Objects.nonNull(datosNuevos.getOrigen()) && !"".equalsIgnoreCase(datosNuevos.getOrigen())) {
            viajeExistente.setOrigen(datosNuevos.getOrigen());
        }
        if (Objects.nonNull(datosNuevos.getFechaInicio())) {
            viajeExistente.setFechaInicio(datosNuevos.getFechaInicio());
        }
        if (Objects.nonNull(datosNuevos.getFechaFin())) {
            viajeExistente.setFechaFin(datosNuevos.getFechaFin());
        }
        if (Objects.nonNull(datosNuevos.getDescripcion()) && !"".equalsIgnoreCase(datosNuevos.getDescripcion())) {
            viajeExistente.setDescripcion(datosNuevos.getDescripcion());
        }
        if (Objects.nonNull(datosNuevos.getTiempoAtm()) && !"".equalsIgnoreCase(datosNuevos.getTiempoAtm())) {
            viajeExistente.setTiempoAtm(datosNuevos.getTiempoAtm());
        }
        if (Objects.nonNull(datosNuevos.getDistanciaEntreVuelos())) {
            viajeExistente.setDistanciaEntreVuelos(datosNuevos.getDistanciaEntreVuelos());
        }

        // Recalcular precio si cambian fechas o relaciones
        viajeExistente.setPrecio(calcularPrecioViaje(viajeExistente));

        return mapToDTO(vr.save(viajeExistente));
    }

    @Transactional
    public void deleteViaje(Long id) {
        Viaje viaje = vr.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado con id: " + id));

        Pedido pedido = viaje.getPedido();
        vr.deleteById(id);

        // Necesario recalcular el precio pedido
        if (Objects.nonNull(pedido)) {
            double nuevoTotal = pedido.getViajes().stream()
                    .filter(v -> !v.getId_viaje().equals(id))
                    .mapToDouble(this::calcularPrecioViaje)
                    .sum();
            pedido.setPrecioTotal(nuevoTotal * pedido.getNumeroPersonas());
            pr.save(pedido);
        }
    }

    @Transactional(readOnly = true)
    public List<ViajeResponseDTO> getViajesByPedido(Long idPedido) {
        return vr.findByPedido_IdPedido(idPedido).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ViajeResponseDTO> getViajesByOrigen(String origen) {
        return vr.findByOrigenContaining(origen).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ViajeResponseDTO> getViajesByFechas(LocalDate inicio, LocalDate fin) {
        return vr.findByFechaInicioBetween(inicio, fin).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}