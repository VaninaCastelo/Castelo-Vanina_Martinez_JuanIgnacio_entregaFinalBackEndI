package com.integrador.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.integrador.entity.Turno;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)

    public class TurnoDto {
        private Long id;
        private String paciente;
        private String odontologo;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
        private LocalDateTime fecha;

        public TurnoDto() {
        }

        public TurnoDto(Long id, String paciente, String odontologo, LocalDateTime fecha) {
            this.id = id;
            this.paciente = paciente;
            this.odontologo = odontologo;
            this.fecha = fecha;
        }

        public Long getId(){ return id; }


        public String getPaciente() {
            return paciente;
        }


        public void setPaciente(String paciente) {
            this.paciente = paciente;
        }

        public String getOdontologo() {
            return odontologo;
        }

        public void setOdontologo(String odontologo) {
            this.odontologo = odontologo;
        }

        public LocalDateTime getFecha() {
            return fecha;
        }

        public void setFecha(LocalDateTime fecha) {
            this.fecha = fecha;
        }

        public static TurnoDto fromTurno(Turno turno) {
            String paciente = turno.getPaciente().getNombre() + " " + turno.getPaciente().getApellido();
            String odontologo = turno.getOdontologo().getNombre() + " " + turno.getOdontologo().getApellido();
            return new TurnoDto(turno.getId(),paciente, odontologo, turno.getFecha());
        }


}
