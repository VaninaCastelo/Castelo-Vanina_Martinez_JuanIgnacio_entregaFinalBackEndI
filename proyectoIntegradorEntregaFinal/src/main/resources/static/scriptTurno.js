/////////////////////REGISTRAR TURNOS/////////////////////////////////////////

// Asociar evento de clic al botón "Registrar turno"
const botonRegistrar = document.getElementById('botonRegistrar');
botonRegistrar.addEventListener('click', registrar);

// Función para registrar el turno
function registrar() {
  // Obtener los valores seleccionados de los select de paciente y odontólogo
  const pacienteSelect = document.getElementById('pacienteSelect');
  const odontologoSelect = document.getElementById('odontologoSelect');

  const pacienteId = pacienteSelect.value;
  const odontologoId = odontologoSelect.value;

  const fechaHoraInput = document.getElementById('fechaHoraInput');
  var fechaHoraSinT = fechaHoraInput.value.replace("T", " ");
  console.log(fechaHoraSinT);
    const fechaHora = JSON.stringify(fechaHoraSinT);
    console.log(fechaHora);

  // Obtener el objeto completo de Paciente basado en el pacienteId
  obtenerPaciente(pacienteId)
    .then(pacienteSeleccionado => {
      // Obtener el objeto completo de Odontologo basado en el odontologoId
      obtenerOdontologo(odontologoId)
        .then(odontologoSeleccionado => {
          // Crear el objeto de turno con el paciente y odontólogo seleccionados
          // Obtener el valor de la fecha y hora seleccionadas en el formulario
    

// Crear el objeto de turno con el paciente, odontólogo y fecha y hora
    const turno = {
        paciente: pacienteSeleccionado,
        odontologo: odontologoSeleccionado,
        fechaHora: fechaHora,
    };


          // Enviar el objeto de turno al backend
          fetch('turnos/registrar', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(turno),
          })
            .then(response => {
              
            })
            .catch(error => {
             
            });
        })
        .catch(error => {
          console.error('Error al obtener el odontólogo:', error);
        });
    })
    .catch(error => {
      console.error('Error al obtener el paciente:', error);
    });
}



function obtenerPaciente(pacienteId) {
    return fetch(`/pacientes/${pacienteId}`)
      .then(response => response.json())
      .then(data => data)
      .catch(error => {
        console.error('Error al obtener el paciente:', error);
        return null;
      });
  }

  function obtenerOdontologo(odontologoId) {
    return fetch(`/odontologos/${odontologoId}`)
      .then(response => response.json())
      .then(data => data)
      .catch(error => {
        console.error('Error al obtener el paciente:', error);
        return null;
      });
  }


  

    

// Obtener lista de pacientes desde el backend
fetch('/pacientes')
    .then(response => response.json())
    .then(data => {
        console.log(data);
        const pacienteSelect = document.getElementById('pacienteSelect');
        data.forEach(paciente => {
            const option = document.createElement('option');
            option.text = `${paciente.nombre} ${paciente.apellido}`;
            option.value = paciente.id;
            pacienteSelect.add(option);
            pacienteSelect.appendChild(option);
        });
    });

// Obtener lista de odontólogos desde el backend
fetch('/odontologos')
    .then(response => response.json())
    .then(data => {
        const odontologoSelect = document.getElementById('odontologoSelect');
        data.forEach(odontologo => {
            const option = document.createElement('option');
            option.text = `${odontologo.nombre} ${odontologo.apellido}`;
            option.value = odontologo.id;
            odontologoSelect.appendChild(option);
        });
    });

    function registrarTurno() {
        const pacienteId = document.getElementById('pacienteSelect').value;
        const odontologoId = document.getElementById('odontologoSelect').value;
        const fecha = document.getElementById('fechaHoraInput').value;
    
        const data = {
            pacienteId: pacienteId,
            odontologoId: odontologoId,
            fecha: JSON.stringify(fecha)
        };
    
       
        

    }
    