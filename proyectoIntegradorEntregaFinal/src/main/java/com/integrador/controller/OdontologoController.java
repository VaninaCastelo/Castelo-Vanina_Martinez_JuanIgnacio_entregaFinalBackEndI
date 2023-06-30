package com.integrador.controller;

import com.integrador.dto.OdontologoDto;
import com.integrador.entity.Odontologo;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;
import com.integrador.service.IOdontologoService;
import com.integrador.service.impl.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/odontologos")
public class OdontologoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private IOdontologoService odontologoService;

    @Autowired
    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }


    //GET
    @Operation(summary = "Listado de todos los odontologos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public List<OdontologoDto> listarOdontologos() throws ResourceNotFoundException {
        return odontologoService.listarOdontologos();
    }

    @Operation(summary = "Buscar odontologo por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Odontologo encontrado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarOdontologoPorId(@PathVariable Long id) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<>(odontologoService.buscarOdontologoPorId(id), null, HttpStatus.OK);
    }

    //POST
    @Operation(summary = "Registro de odontologo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Odontologo registrado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OdontologoDto> registrarOdontologo(@Valid @RequestBody Odontologo odontologo) throws BadRequestException {
        LOGGER.info("que pasaaa");
        return new ResponseEntity<>(odontologoService.registrarOdontologo(odontologo), null, HttpStatus.CREATED);
    }

    //PUT
    @Operation(summary = "Actualizar de odontologo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Odontologo actualizado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PutMapping("/actualizar")
    public OdontologoDto actualizarOdontologo(@Valid @RequestBody Odontologo odontologo) throws BadRequestException, ResourceNotFoundException {
        return odontologoService.actualizarOdontologo(odontologo);
    }

    //DELETE
    @Operation(summary = "Eliminar de odontologo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Odontologo eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Long id) throws BadRequestException, ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok("Odontologo eliminado");
    }

}