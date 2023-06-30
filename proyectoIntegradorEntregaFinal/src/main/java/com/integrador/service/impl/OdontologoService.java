package com.integrador.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrador.dto.OdontologoDto;
import com.integrador.entity.Odontologo;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;
import com.integrador.repository.IOdontologoRepository;
import com.integrador.service.IOdontologoService;
import com.integrador.utils.JsonPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private final IOdontologoRepository odontologoRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public OdontologoService(IOdontologoRepository odontologoRepository, ObjectMapper objectMapper){
        this.odontologoRepository = odontologoRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public OdontologoDto buscarOdontologoPorId(Long id) throws BadRequestException, ResourceNotFoundException {
        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        OdontologoDto odontologoDto = null;
        if (odontologoBuscado != null) {
            odontologoDto = objectMapper.convertValue(odontologoBuscado, OdontologoDto.class);

            LOGGER.info("Odontologo encontrado: {}", JsonPrinter.toString(odontologoDto));

        } else LOGGER.info("El id no se encuentra registrado en la base de datos");

        return odontologoDto;
    }

    @Override
    public List<OdontologoDto> listarOdontologos() throws ResourceNotFoundException{
        List<Odontologo> odontologos = odontologoRepository.findAll();
        List<OdontologoDto> odontologosDto = odontologos.stream()
                .map(odontologo -> {
                    return new OdontologoDto(odontologo.getId(),odontologo.getMatricula(), odontologo.getNombre(), odontologo.getApellido());
                })
                .toList();

        LOGGER.info("Lista de todos los odontologos: {}", odontologosDto);
        return odontologosDto;
    }

    @Override
    public OdontologoDto registrarOdontologo(Odontologo odontologo) throws BadRequestException{
        Odontologo odontologoNuevo = odontologoRepository.save(odontologo);
        OdontologoDto odontologoDtoNuevo = objectMapper.convertValue(odontologoNuevo, OdontologoDto.class);

        LOGGER.info("Nuevo odontologo registrado con exito: {}", odontologoDtoNuevo);

        return odontologoDtoNuevo;
    }

    @Override
    public OdontologoDto actualizarOdontologo(Odontologo odontologo) throws ResourceNotFoundException, BadRequestException{
        Odontologo odontologoAActualizar = odontologoRepository.findById(odontologo.getId()).orElse(null);
        OdontologoDto odontologoActualizadoDto = null;

        if (odontologoAActualizar != null) {
            odontologoAActualizar = odontologo;
            odontologoRepository.save(odontologoAActualizar);

            odontologoActualizadoDto = objectMapper.convertValue(odontologoAActualizar, OdontologoDto.class);
            LOGGER.info("Odontologo actualizado con exito: {}", odontologoActualizadoDto);

        } else LOGGER.error("No fue posible actualizar los datos ya que el odontologo no se encuentra registrado");

        return odontologoActualizadoDto;
    }

    @Override
    public void eliminarOdontologo(Long id) throws BadRequestException, ResourceNotFoundException{
        odontologoRepository.deleteById(id);
        LOGGER.warn("Se ha eliminado el odontologo con id {}", id);
    }
}