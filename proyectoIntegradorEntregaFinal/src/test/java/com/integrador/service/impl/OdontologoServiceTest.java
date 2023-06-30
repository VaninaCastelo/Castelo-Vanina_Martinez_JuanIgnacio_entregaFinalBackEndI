package com.integrador.service.impl;

import com.integrador.dto.OdontologoDto;
import com.integrador.entity.Odontologo;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaInsertarUnOdontologo() throws BadRequestException {
        Odontologo odontologoAInsertar = new Odontologo("MAT2453", "Carlos", "Muela");
        OdontologoDto odontologoDto = odontologoService.registrarOdontologo(odontologoAInsertar);

        Assertions.assertNotNull(odontologoDto);
        Assertions.assertNotNull(odontologoDto.getId());
    }

    @Test
    @Order(2)
    void noDeberiaRegistrarOdontologoSinMatricula() {
        Odontologo odontologo = new Odontologo("1234", "Marcela", "Canino");
        assertTrue(odontologo.getMatricula() != null);
    }

    @Test
    @Order(3)
    void deberiaListarUnSoloOdontologo() throws ResourceNotFoundException {
        List<OdontologoDto> odontologoDtos = odontologoService.listarOdontologos();
        assertEquals(1, odontologoDtos.size());
    }

    @Test
    @Order(4)
    void deberiaEliminarElOdontologo() throws BadRequestException, ResourceNotFoundException {
        odontologoService.eliminarOdontologo(1L);

    }


}