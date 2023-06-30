package com.integrador.controller;

import com.integrador.dto.OdontologoDto;
import com.integrador.dto.PacienteDto;
import com.integrador.entity.Paciente;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;
import com.integrador.service.IPacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@CrossOrigin
public class PacienteController {
    private IPacienteService pacienteService;

    @Autowired
    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    //POST
    @Operation(summary = "Registrar paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente registrado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/registrar")
    public ResponseEntity<PacienteDto> registrarPaciente(@RequestBody Paciente paciente) throws BadRequestException {
        ResponseEntity<PacienteDto> respuesta;
        PacienteDto pacienteDto = pacienteService.guardarPaciente(paciente);
        if (pacienteDto != null) respuesta = new ResponseEntity<>(pacienteDto, null, HttpStatus.CREATED);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return respuesta;
    }

    //PUT
    @Operation(summary = "Actualizar paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente actualizado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/actualizar")
    public ResponseEntity<PacienteDto> actualizarPaciente(@RequestBody Paciente paciente) throws BadRequestException, ResourceNotFoundException {
        ResponseEntity<PacienteDto> respuesta;
        PacienteDto pacienteDto = pacienteService.actualizarPaciente(paciente);
        if (pacienteDto != null) respuesta = new ResponseEntity<>(pacienteDto, null, HttpStatus.OK);
        else respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return respuesta;
    }

    //GET
    @Operation(summary = "Listar todos los pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recuerso obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<PacienteDto> listarTodos() throws ResourceNotFoundException {
        return pacienteService.listarPacientes();
    }

    @Operation(summary = "Buscar paciente por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de todos los PACIENTES",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> buscarPacientePorId(@PathVariable Long id) throws BadRequestException, ResourceNotFoundException {
        ResponseEntity<PacienteDto> respuesta;
        PacienteDto pacienteDto = pacienteService.buscarPacientePorId(id);
        if (pacienteDto != null) respuesta = new ResponseEntity<>(pacienteDto, null, HttpStatus.OK);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return respuesta;
    }

    @Operation(summary = "Buscar paciente por apellido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/apellido/{apellido}")
    public List<PacienteDto> buscarPacientesPorApellido(@PathVariable String apellido) throws BadRequestException, ResourceNotFoundException {
        return pacienteService.buscarPacientesPorApellido(apellido);
    }

    //DELETE
    @Operation(summary = "Eliminar paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) throws BadRequestException, ResourceNotFoundException {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("Paciente eliminado");
    }

}
