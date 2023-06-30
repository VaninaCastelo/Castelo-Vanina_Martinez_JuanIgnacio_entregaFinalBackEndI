package com.integrador.repository;

import com.integrador.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("SELECT paciente FROM Paciente paciente WHERE LOWER(paciente.apellido) LIKE LOWER(concat('%', :apellido, '%'))")
    List<Paciente> buscarPacientePorApellido(@Param("apellido") String apellido);



}
