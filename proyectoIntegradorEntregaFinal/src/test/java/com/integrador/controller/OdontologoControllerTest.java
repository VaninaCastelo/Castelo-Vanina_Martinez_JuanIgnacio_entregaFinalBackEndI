package com.integrador.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.integrador.dto.OdontologoDto;
import com.integrador.entity.Odontologo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class OdontologoControllerTest {

    @Autowired
    private OdontologoController odontologoController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
void deberiaRegistrarUnOdontologo() throws Exception {
        Odontologo odontologo = new Odontologo("MAT1234","Carlos", "Muela");
        OdontologoDto odontologoDto = new OdontologoDto(1L,"MAT1234","Carlos","Muela");

        ObjectWriter writer = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false).writer();
        String playload = writer.writeValueAsString(odontologo);
        String expectedResponse = writer.writeValueAsString(odontologoDto);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/odontologos/registrar")
                        .content(String.valueOf(playload))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                .andExpect(status().is(201))
                .andExpect(content().contentType("aplication/json"))
                .andReturn();

        Assertions.assertEquals(expectedResponse, response.getResponse().getContentAsString());
    }

    @Test
    @Order(2)
    void deberiaEncontrarOdontologoConId1PorApi() throws Exception {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/odontologos/{id}",1))
                    .andDo(print())
                    .andExpect(content().contentType("aplication/json"))
                    .andExpect(status().is(200))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.matricula").value("MAT1234"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.apellido").value("Muela"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    }
