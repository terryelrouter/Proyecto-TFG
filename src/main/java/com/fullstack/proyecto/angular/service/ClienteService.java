package com.fullstack.proyecto.angular.service;

import com.fullstack.proyecto.angular.dto.ClienteRequestDTO;
import com.fullstack.proyecto.angular.dto.ClienteResponseDTO;
import com.fullstack.proyecto.angular.dto.ClienteUpdateDTO;
import com.fullstack.proyecto.angular.dto.LoginDTO;
import com.fullstack.proyecto.angular.enums.RolEnum;
import com.fullstack.proyecto.angular.model.Cliente;
import com.fullstack.proyecto.angular.security.JwtUtil;
import com.fullstack.proyecto.angular.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private final PasswordEncoder passwordEncoder;
    private final ClienteRepository cr;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public ClienteService(ClienteRepository cr, PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
        this.cr = cr;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil=jwtUtil;
    }

    private ClienteResponseDTO mapToDTO(Cliente c) {
        return ClienteResponseDTO.builder()
                .id(c.getIdCliente())
                .nombre(c.getNombre())
                .apellidos(c.getApellidos())
                .email(c.getEmail())
                .rol(c.getRol())
                .build();
    }

    @Transactional
    public ClienteResponseDTO updateCliente(ClienteUpdateDTO datosNuevos, Long id) {
        Cliente clienteExistente = cr.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));

        if (Objects.nonNull(datosNuevos.getNombre()) && !datosNuevos.getNombre().isEmpty()) {
            clienteExistente.setNombre(datosNuevos.getNombre());
        }
        if (Objects.nonNull(datosNuevos.getApellidos()) && !datosNuevos.getApellidos().isEmpty()) {
            clienteExistente.setApellidos(datosNuevos.getApellidos());
        }
        if (datosNuevos.getEmail() != null &&
                !datosNuevos.getEmail().isEmpty() &&
                !datosNuevos.getEmail().equals(clienteExistente.getEmail()) &&
                cr.existsByEmail(datosNuevos.getEmail())) {
            throw new RuntimeException("Ese email ya está en uso");
        }
        if (datosNuevos.getEmail() != null && !datosNuevos.getEmail().isEmpty()) {
            clienteExistente.setEmail(datosNuevos.getEmail());
        }
        if (Objects.nonNull(datosNuevos.getDireccion()) && !datosNuevos.getDireccion().isEmpty()) {
            clienteExistente.setDireccion(datosNuevos.getDireccion());
        }
        if (Objects.nonNull(datosNuevos.getTelefono()) && !datosNuevos.getTelefono().isEmpty()) {
            clienteExistente.setTelefono(datosNuevos.getTelefono());
        }
        if (Objects.nonNull(datosNuevos.getContrasenya()) && !datosNuevos.getContrasenya().isEmpty()) {
            clienteExistente.setContrasenya(passwordEncoder.encode(datosNuevos.getContrasenya()));
        }

        cr.save(clienteExistente);
        return mapToDTO(clienteExistente);
    }

    @Transactional
    public ClienteResponseDTO registrarCliente(ClienteRequestDTO dto) {
        if (cr.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Cliente cliente = new Cliente();
        cliente.setDni(dto.getDni());
        cliente.setPasaporte(dto.getPasaporte());
        cliente.setNombre(dto.getNombre());
        cliente.setApellidos(dto.getApellidos());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        cliente.setEmail(dto.getEmail());
        cliente.setContrasenya(passwordEncoder.encode(dto.getContrasenya()));
        cliente.setRol(RolEnum.CLIENTE);

        cr.save(cliente);
        return mapToDTO(cliente);
    }

    @Transactional
    public Map<String, Object> loginCliente(LoginDTO dto) {
        Cliente cliente = cr.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getContrasenya(), cliente.getContrasenya())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(
                cliente.getEmail(),
                cliente.getRol().name()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("cliente", mapToDTO(cliente));
        return response;
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO getPerfilCliente(String email) {
        Cliente cliente = cr.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return mapToDTO(cliente);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> getAllClientes() {
        return cr.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO getClienteById(Long id) {
        Cliente cliente = cr.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return mapToDTO(cliente);
    }

    @Transactional
    public void deleteCliente(Long id) {
        if (!cr.existsById(id)) {
            throw new RuntimeException("Cliente no existe");
        }
        cr.deleteById(id);
    }

    @Transactional
    public ClienteResponseDTO cambiarRol(Long id, RolEnum rol) {
        Cliente cliente = cr.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cliente.setRol(rol);
        cr.save(cliente);
        return mapToDTO(cliente);
    }
}


