package com.integrador.service.impl;

import com.integrador.dto.OdontologoDto;
import com.integrador.dto.PacienteDto;
import com.integrador.dto.TurnoDto;
import com.integrador.entity.Domicilio;
import com.integrador.entity.Odontologo;
import com.integrador.entity.Paciente;
import com.integrador.entity.Turno;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurnoServiceTest {

    @Autowired
    PacienteService pacienteService;
    @Autowired
    OdontologoService odontologoService;
    @Autowired
    TurnoService turnoService;

    @Test
    @Order(1)
    void deberiaRegistrarUnTurno() throws BadRequestException, ResourceNotFoundException {
        Paciente pacienteARegistrar = new Paciente("Juan","Perez", "30547842", LocalDate.of(2020,10,10),new Domicilio("Las Heras",547,"Mar del Plata","Buenos Aires"));
        PacienteDto pacienteDto = pacienteService.guardarPaciente(pacienteARegistrar);

        Odontologo odontologoARegistrar = new Odontologo("Marcelo","Lopez","MAT2345");
        OdontologoDto odontologoDto = odontologoService.registrarOdontologo(odontologoARegistrar);

        Turno turnoARegistrar = new Turno(pacienteARegistrar,odontologoARegistrar, LocalDateTime.of(2023,10,5,15,30,0));
        TurnoDto turnoDto = turnoService.guardarTurno(turnoARegistrar);

    }

    @Test
    @Order(2)
    void deberiaListarTurnos() throws ResourceNotFoundException{
        List<TurnoDto> turnoDtos = turnoService.listarTodos();
        assertTrue(turnoDtos.size() > 0);
    }

    @Test
    @Order(3)
    void noDebeRegistrarTurnoSinOdontologo() throws BadRequestException {
        Paciente pacienteARegistrar = new Paciente("Juan","Perez", "30547842", LocalDate.of(2020,10,10),new Domicilio("Las Heras",547,"Mar del Plata","Buenos Aires"));
        PacienteDto pacienteDto = pacienteService.guardarPaciente(pacienteARegistrar);

        Odontologo odontologo = null;
        LocalDateTime fecha = LocalDateTime.now();

        assertThrows(NullPointerException.class, () -> turnoService.guardarTurno(new Turno(pacienteARegistrar,odontologo,fecha)));


    }

}