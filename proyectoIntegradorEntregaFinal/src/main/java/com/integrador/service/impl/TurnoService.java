package com.integrador.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrador.dto.OdontologoDto;
import com.integrador.dto.PacienteDto;
import com.integrador.dto.TurnoDto;
import com.integrador.entity.Odontologo;
import com.integrador.entity.Paciente;
import com.integrador.entity.Turno;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;
import com.integrador.repository.ITurnoRepository;
import com.integrador.service.ITurnoService;
import com.integrador.utils.JsonPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final ITurnoRepository turnoRepository;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    @Autowired
    public TurnoService(ITurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

       @Override
    public TurnoDto guardarTurno(Turno turno) throws BadRequestException, ResourceNotFoundException {
        TurnoDto turnoDto = null;
        PacienteDto paciente = pacienteService.buscarPacientePorId(turno.getPaciente().getId());
        OdontologoDto odontologo = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());

        if(paciente == null || odontologo == null) {
            if(paciente == null && odontologo == null) {
                LOGGER.error("El paciente y el odontologo no se encuentran en nuestra base de datos");
                throw new BadRequestException("El paciente no se encuentra en nuestra base de datos");
            }
            else if (paciente == null){
                LOGGER.error("El paciente no se encuentra en nuestra base de datos");
                throw new BadRequestException("El paciente no se encuentra en nuestra base de datos");
            } else {
                LOGGER.error("El odontologo no se encuentra en nuestra base de datos");
                throw new BadRequestException("El odontologo no se encuentra en nuestra base de datos");
            }

        } else {
            turnoDto = TurnoDto.fromTurno(turnoRepository.save(turno));
            LOGGER.info("Nuevo turno registrado con exito: {}", JsonPrinter.toString(turnoDto));
        }

        return turnoDto;
    }

    @Override
    public List<TurnoDto> listarTodos() throws ResourceNotFoundException{
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoDto> turnoDtoList = turnos.stream()
                .map(TurnoDto::fromTurno)
                .toList();

        LOGGER.info("Lista de todos los turnos: {}", JsonPrinter.toString(turnoDtoList));
        return turnoDtoList;
    }

    @Override
    public TurnoDto buscarTurnoPorId(Long id) throws BadRequestException, ResourceNotFoundException{
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoDto turnoDto = null;
        if (turnoBuscado != null) {
            turnoDto = TurnoDto.fromTurno(turnoBuscado);
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoDto));
        } else {
            LOGGER.info("El id no se encuentra registrado en la base de datos");


        }
        return turnoDto;
    }

    @Override
    public TurnoDto actualizarTurno(Turno turno) throws BadRequestException, ResourceNotFoundException{
        Turno turnoAActualizar = turnoRepository.findById(turno.getId()).orElse(null);
        TurnoDto turnoDtoActualizado = null;
        if (turnoAActualizar != null) {
            turnoAActualizar = turno;
            turnoRepository.save(turnoAActualizar);
            turnoDtoActualizado = TurnoDto.fromTurno(turnoAActualizar);
            LOGGER.warn("Turno actualizado: {}", JsonPrinter.toString(turnoDtoActualizado));
        } else {
            LOGGER.error("No fue posible actualizar los datos ya que el turno no se encuentra registrado");

        }

        return turnoDtoActualizado;
    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException, BadRequestException {
        if (buscarTurnoPorId(id) != null) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id {}", id);
        } else {
            LOGGER.error("No se ha encontrado el turno con id {}", id);
            throw new ResourceNotFoundException("No se ha encontrado el turno con id " + id);
        }
    }

}
