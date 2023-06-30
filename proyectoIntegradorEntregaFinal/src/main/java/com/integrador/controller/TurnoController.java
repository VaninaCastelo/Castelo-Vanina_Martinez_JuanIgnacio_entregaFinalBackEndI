package com.integrador.controller;

import com.integrador.dto.TurnoDto;
import com.integrador.entity.Turno;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;
import com.integrador.service.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
@CrossOrigin
public class TurnoController {
    private ITurnoService turnoService;

    @Autowired
    public TurnoController(ITurnoService turnoService){this.turnoService = turnoService;}

    //POST
    @PostMapping("/registrar")
    public ResponseEntity<TurnoDto> registrarTurno(@RequestBody Turno turno) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<>(turnoService.guardarTurno(turno), null, HttpStatus.CREATED);
    }

    //PUT
    @PutMapping("/actualizar")
    public ResponseEntity<TurnoDto> actualizarTurno(@RequestBody Turno turno) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<TurnoDto>(turnoService.actualizarTurno(turno), null, HttpStatus.OK);
    }

    //GET
    @GetMapping
    public List<TurnoDto> listarTodos() throws ResourceNotFoundException {
        return turnoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTurnoPorId(@PathVariable Long id) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<>(turnoService.buscarTurnoPorId(id), null, HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException, BadRequestException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("Turno eliminado");
    }


}
