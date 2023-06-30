package com.integrador.service;

import com.integrador.dto.TurnoDto;
import com.integrador.entity.Turno;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;

import java.util.List;

public interface ITurnoService {
    TurnoDto guardarTurno(Turno turno) throws BadRequestException, ResourceNotFoundException;

    List<TurnoDto> listarTodos() throws ResourceNotFoundException;

    TurnoDto buscarTurnoPorId(Long id) throws BadRequestException, ResourceNotFoundException;

    TurnoDto actualizarTurno(Turno turno) throws BadRequestException, ResourceNotFoundException;

    void eliminarTurno(Long id) throws ResourceNotFoundException, BadRequestException;
}
