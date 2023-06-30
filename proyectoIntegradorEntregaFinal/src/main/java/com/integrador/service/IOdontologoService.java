package com.integrador.service;

import com.integrador.dto.OdontologoDto;
import com.integrador.entity.Odontologo;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;

import java.util.List;

public interface IOdontologoService {
    OdontologoDto buscarOdontologoPorId(Long id) throws BadRequestException, ResourceNotFoundException;

    List<OdontologoDto> listarOdontologos() throws ResourceNotFoundException;

    OdontologoDto registrarOdontologo(Odontologo odontologo) throws BadRequestException;

    OdontologoDto actualizarOdontologo(Odontologo odontologo) throws ResourceNotFoundException, BadRequestException;

    void eliminarOdontologo(Long id) throws BadRequestException, ResourceNotFoundException;
}