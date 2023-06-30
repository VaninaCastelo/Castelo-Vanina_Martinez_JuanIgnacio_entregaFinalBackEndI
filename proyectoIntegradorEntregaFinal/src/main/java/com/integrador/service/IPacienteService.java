package com.integrador.service;

import com.integrador.dto.PacienteDto;
import com.integrador.entity.Paciente;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;

import java.util.List;

public interface IPacienteService {
    List<PacienteDto> listarPacientes() throws ResourceNotFoundException;

    PacienteDto buscarPacientePorId(Long id) throws BadRequestException, ResourceNotFoundException;

    PacienteDto guardarPaciente(Paciente paciente) throws BadRequestException;

    PacienteDto actualizarPaciente(Paciente paciente) throws BadRequestException, ResourceNotFoundException;

    List<PacienteDto> buscarPacientesPorApellido(String apellido) throws ResourceNotFoundException, BadRequestException;

    void eliminarPaciente(Long id) throws BadRequestException, ResourceNotFoundException;

}