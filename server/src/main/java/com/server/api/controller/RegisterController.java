package com.server.api.controller;

import com.server.api.dto.CiudadDTO;
import com.server.api.dto.DepartamentoDTO;
import com.server.api.dto.PaisDTO;
import com.server.api.dto.SedeDTO;
import com.server.api.dto.ValoresListaDTO;
import com.server.api.model.Ciudad;
import com.server.api.model.Departamento;
import com.server.api.model.Pais;
import com.server.api.model.Sede;
import com.server.api.model.ValoresLista;
import com.server.api.repository.CiudadRepository;
import com.server.api.repository.DepartamentoRepository;
import com.server.api.repository.PaisRepository;
import com.server.api.repository.SedeRepository;
import com.server.api.service.RegisterService;
import com.server.api.service.ValoresListaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/register")
@SecurityRequirement(name = "Bearer Auth")
@CrossOrigin(origins = "*")
public class RegisterController {

    @Autowired
    private ValoresListaService valoresListaService;

    @Autowired
    private RegisterService registerService;

    // Obtener todos los países habilitados
    @GetMapping("/paises")
    public ResponseEntity<List<PaisDTO>> getAllPaises() {
        return ResponseEntity.ok(registerService.getAllPaises());
    }

    // Obtener departamentos por país
    @GetMapping("/paises/{paisId}/departamentos")
    public ResponseEntity<List<DepartamentoDTO>> getDepartamentosByPais(@PathVariable Long paisId) {
        return ResponseEntity.ok(registerService.getDepartamentosByPais(paisId));
    }

    // Obtener ciudades por departamento
    @GetMapping("/departamentos/{departamentoId}/ciudades")
    public ResponseEntity<List<CiudadDTO>> getCiudadesByDepartamento(@PathVariable Long departamentoId) {
        return ResponseEntity.ok(registerService.getCiudadesByDepartamento(departamentoId));
    }

    // Obtener sedes por ciudad
    @GetMapping("/ciudades/{ciudadId}/sedes")
    public ResponseEntity<List<SedeDTO>> getSedesByCiudad(@PathVariable Long ciudadId) {
        return ResponseEntity.ok(registerService.getSedesByCiudad(ciudadId));
    }

    // Obtener valores habilitados de una lista por su ID
    @GetMapping("/listas/{listaId}/valores")
    public ResponseEntity<List<ValoresListaDTO>> getValoresByLista(@PathVariable Long listaId) {
        List<ValoresListaDTO> valores = valoresListaService.getValoresByListaId(listaId);
        return ResponseEntity.ok(valores);
    }
}
