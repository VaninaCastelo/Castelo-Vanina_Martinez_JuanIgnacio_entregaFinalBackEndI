package com.integrador.service.impl;


import com.integrador.dto.PacienteDto;
import com.integrador.entity.Domicilio;
import com.integrador.entity.Paciente;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PacienteServiceTest {

    @Autowired
    PacienteService pacienteService;

    @Test
    @Order(1)
    void deberiaRegistrarUnPaciente() throws BadRequestException {
        Paciente pacienteARegistrar = new Paciente("Juan","Perez", "30547842", LocalDate.of(2020,10,10),new Domicilio("Las Heras",547,"Mar del Plata","Buenos Aires"));
        PacienteDto pacienteDto = pacienteService.guardarPaciente(pacienteARegistrar);

        Assertions.assertNotNull(pacienteDto);
        Assertions.assertNotNull(pacienteDto.getId());
    }

    @Test
    @Order(2)
    void deberiaListarPacientes() throws ResourceNotFoundException{
        List<PacienteDto> pacienteDtos = pacienteService.listarPacientes();
        assertTrue(pacienteDtos.size() > 0);
    }

    @Test
    @Order(3)
    void deberiaEncontrarPacientePorId() throws ResourceNotFoundException, BadRequestException{
        PacienteDto pacienteEncontrado = pacienteService.buscarPacientePorId(1L);
        assertEquals(1,pacienteEncontrado.getId());
    }

    @Test
    @Order(4)
    void deberiaEliminarPacientePorId() throws ResourceNotFoundException, BadRequestException{
        pacienteService.eliminarPaciente(1L);
        assertThrows(ResourceNotFoundException.class, () -> pacienteService.buscarPacientePorId(1L));
    }

}