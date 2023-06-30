window.addEventListener('load', function () {

    /*var listadoPacientesBtn = document.getElementById('listado-pac');
    var listadoOdontologosBtn = document.getElementById('listado-odont');
    var listadoTurnosBtn = document.getElementById('listado-turnos');
    
    listadoPacientesBtn.addEventListener('click', function() {
        // Lógica para acceder al listado de pacientes
        console.log("Accediendo al listado de pacientes...");
    });
    // Obtener referencias a los elementos
    var nuevoPacienteBtn = document.getElementById('nuevo-pac');
    var formAgregarPaciente = document.getElementById('formAgregarPac');
    
    // Array para almacenar los pacientes
    var listadoPacientes = [];
    
    // Función para agregar un nuevo paciente
    function agregarPaciente(event) {
        event.preventDefault(); // Evitar el envío del formulario
        */

        ////////////REGISTRAR PACIENTES////////////////////////////////

    // Obtener los valores del formulario
    const formularioPaciente = document.querySelector('#formAgregarPac');

    formularioPaciente.addEventListener('submit', function (event) {

        const formDataPaciente = {
            nombre: document.querySelector('#nombrePaciente').value,
            apellido: document.querySelector('#apellidoPaciente').value,
            dni: document.querySelector('#dniPaciente').value,
            domicilio: {
                calle: document.querySelector('#callePaciente').value,
                numero: document.querySelector('#numeroPaciente').value,
                localidad: document.querySelector('#localidadPaciente').value,
                provincia: document.querySelector('#provinciaPaciente').value
            }
        }

        const url = '/pacientes/registrar';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formDataPaciente)
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                //Si no hay ningun error se muestra un mensaje diciendo que el paciente
                //se agrego bien
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong></strong> Odontólogo registrado </div>'

                document.querySelector('#response').innerHTML = successAlert;
                document.querySelector('#response').style.display = "block";
                resetUploadForm();

            })
         /*   .catch(error => {
                //Si hay algun error se muestra un mensaje diciendo que el paciente
                //no se pudo guardar y se intente nuevamente
                let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong> Error intente nuevamente</strong> </div>'

                document.querySelector('#response').innerHTML = errorAlert;
                document.querySelector('#response').style.display = "block";
                //se dejan todos los campos vacíos por si se quiere ingresar otro paciente
                resetUploadForm();
            })*/
    });


    function resetUploadForm() {
        document.querySelector('#nombrePaciente').value = "";
        document.querySelector('#apellidoPaciente').value = "";
        document.querySelector('#dniPaciente').value = "";
        document.querySelector('#callePaciente').value = "";
        document.querySelector('#numeroPaciente').value = "";
        document.querySelector('#localidadPaciente').value = "";
        document.querySelector('#provinciaPaciente').value = "";
        
    }

  /*  (function () {
        let pathname = window.location.pathname;
        if (pathname === "/") {
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/odontologoList.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();  */

 //////////////////REGISTRAR ODONTOLOGOS///////////////////////////

 const formularioOdontologo = document.querySelector('#formAgregarOdont');

 formularioPaciente.addEventListener('submit', function (event) {

     const formDataOdontologo = {
         nombre: document.querySelector('#nombreOdontologo').value,
         apellido: document.querySelector('#apellidoOdontologo').value,
         matricula: document.querySelector('#matriculaOdontologo').value,
     }

     const url = '/odontologos/registrar';
     const settings = {
         method: 'POST',
         headers: {
             'Content-Type': 'application/json',
         },
         body: JSON.stringify(formDataOdontologo)
     }

     fetch(url, settings)
         .then(response => response.json())
         .then(data => {
             resetUploadForm();
         })
     
 });


 function resetUploadForm() {
     document.querySelector('#nombrePaciente').value = "";
     document.querySelector('#apellidoPaciente').value = "";
     document.querySelector('#dniPaciente').value = "";
     document.querySelector('#callePaciente').value = "";
     document.querySelector('#numeroPaciente').value = "";
     document.querySelector('#localidadPaciente').value = "";
     document.querySelector('#provinciaPaciente').value = "";
     
 }


///////////////////////////////TURNOS///////////////////////////////////////////////
// Obtener la tabla de turnos y el botón "Listado de turnos"
var tablaTurnos = document.getElementById('tabla-turnos');
var botonListadoTurnos = document.getElementById('listado-turnos');

// Ocultar la tabla de turnos al cargar la página
tablaTurnos.style.display = 'none';

// Agregar el evento clic al botón "Listado de turnos"
botonListadoTurnos.addEventListener('click', function() {
  // Mostrar la tabla de turnos al hacer clic en el botón
  tablaTurnos.style.display = 'table';
});
// Obtener referencia al botón de listado de turnos
const listadoTurnosBtn = document.getElementById('listado-turnos');

// Asociar evento de clic al botón
listadoTurnosBtn.addEventListener('click', mostrarListadoTurnos);

// Función para mostrar el listado de turnos
function mostrarListadoTurnos() {
  // Realizar una llamada a tu API para obtener el listado de turnos
  fetch('/turnos')
    .then(response => response.json())
    .then(data => {
      // Obtener referencia al cuerpo de la tabla
      const listaTurnosBody = document.getElementById('lista-turnos-body');
      // Limpiar el contenido previo de la tabla
      listaTurnosBody.innerHTML = '';

      // Recorrer los turnos obtenidos
      data.forEach(turno => {
        // Crear una fila para cada turno
        const filaTurno = document.createElement('tr');

        // Crear las celdas para mostrar el nombre y apellido del paciente
        const celdaPaciente = document.createElement('td');
        celdaPaciente.textContent = turno.paciente;
        filaTurno.appendChild(celdaPaciente);

        // Crear las celdas para mostrar el nombre y apellido del odontólogo
        const celdaOdontologo = document.createElement('td');
        celdaOdontologo.textContent = turno.odontologo;
        filaTurno.appendChild(celdaOdontologo);

        // Crear la celda para mostrar la fecha y hora del turno
        const celdaFechaHora = document.createElement('td');
        if (turno.fecha) {
          const fechaHora = new Date(turno.fecha);
          if (!isNaN(fechaHora)) {
            celdaFechaHora.textContent = fechaHora.toLocaleString(); // Puedes ajustar el formato de fecha y hora según tus preferencias locales
          } else {
            celdaFechaHora.textContent = "Fecha y hora inválida";
          }
        } else {
          celdaFechaHora.textContent = "Fecha y hora no especificada";
        }
        filaTurno.appendChild(celdaFechaHora);

        // Agregar la fila al cuerpo de la tabla
        listaTurnosBody.appendChild(filaTurno);
      });
    })
    .catch(error => {
      console.error('Error al obtener el listado de turnos:', error);
    });
}

// Obtener referencia al botón de registrar turno
const registrarTurnoBtn = document.getElementById('registrar-turno');

// Asociar evento de clic al botón
registrarTurnoBtn.addEventListener('click', redirigirARegistrarTurno);

// Función para redirigir a la página registrarTurno.html
function redirigirARegistrarTurno() {
  window.location.href = 'registroTurno.html';
}

    
})


/*
var nombre = document.getElementById('nombrePaciente').value;
var apellido = document.getElementById('apellidoPaciente').value;

// Crear un objeto paciente
var paciente = {
    nombre: nombre,
    apellido: apellido,
    dni: dni,
    domicilio: {
        calle: calle,
        numero, numero,
        localidad: localidad,
        provincia: provincia
    }
};

// Agregar el paciente al listado
listadoPacientes.push(paciente);

// Limpiar los campos del formulario
document.getElementById('nombrePaciente').value = '';
document.getElementById('apellidoPaciente').value = '';

console.log('Nuevo paciente agregado:', paciente);
console.log('Listado de pacientes:', listadoPacientes);
}

// Asignar función al botón "Nuevo Paciente"
nuevoPacienteBtn.addEventListener('click', function () {
    formAgregarPaciente.style.display = 'block';
});

// Asignar función al formulario de agregar paciente
formAgregarPaciente.addEventListener('submit', agregarPaciente);

listadoOdontologosBtn.addEventListener('click', function () {
    // Lógica para acceder al listado de odontólogos
    console.log("Accediendo al listado de odontólogos...");
});

// Obtener referencias a los elementos
var nuevoPacienteBtn = document.getElementById('nuevo-pac');
var formAgregarPaciente = document.getElementById('formAgregarPac');

// Array para almacenar los pacientes
var listadoPacientes = [];

// Función para agregar un nuevo paciente
function agregarPaciente(event) {
    event.preventDefault(); // Evitar el envío del formulario

    // Obtener los valores del formulario
    var nombre = document.getElementById('nombrePaciente').value;
    var apellido = document.getElementById('apellidoPaciente').value;

    // Crear un objeto paciente
    var paciente = {
        nombre: nombre,
        apellido: apellido
    };

    // Agregar el paciente al listado
    listadoPacientes.push(paciente);

    // Limpiar los campos del formulario
    document.getElementById('nombrePaciente').value = '';
    document.getElementById('apellidoPaciente').value = '';

    console.log('Nuevo paciente agregado:', paciente);
    console.log('Listado de pacientes:', listadoPacientes);
}

// Asignar función al botón "Nuevo Paciente"
nuevoPacienteBtn.addEventListener('click', function () {
    formAgregarPaciente.style.display = 'block';
});

// Asignar función al formulario de agregar paciente
formAgregarPaciente.addEventListener('submit', agregarPaciente);

// Obtener referencias a los elementos
var nuevoOdontologoBtn = document.getElementById('nuevo-odont');
var formAgregarOdontologo = document.getElementById('formAgregarOdont');

// Array para almacenar los odontólogos
var listadoOdontologos = [];

// Función para agregar un nuevo odontólogo
function agregarOdontologo(event) {
    event.preventDefault(); // Evitar el envío del formulario

    // Obtener los valores del formulario
    var nombre = document.getElementById('nombreOdontologo').value;
    var apellido = document.getElementById('apellidoOdontologo').value;

    // Crear un objeto odontólogo
    var odontologo = {
        nombre: nombre,
        apellido: apellido
    };

    // Agregar el odontólogo al listado
    listadoOdontologos.push(odontologo);

    // Limpiar los campos del formulario
    document.getElementById('nombreOdontologo').value = '';
    document.getElementById('apellidoOdontologo').value = '';

    console.log('Nuevo odontólogo agregado:', odontologo);
    console.log('Listado de odontólogos:', listadoOdontologos);
}

// Asignar función al botón "Nuevo Odontólogo"
nuevoOdontologoBtn.addEventListener('click', function () {
    formAgregarOdontologo.style.display = 'block';
});

// Asignar función al formulario de agregar odontólogo

formAgregarOdontologo.addEventListener('submit', agregarOdontologo);

listadoTurnosBtn.addEventListener('click', function () {
    // Lógica para acceder al listado de turnos
    console.log("Accediendo al listado de turnos...");
})

// Obtener referencias a los elementos
var nuevoTurnoBtn = document.getElementById('nuevo-turno');
var formAgregarTurno = document.getElementById('formAgregarTurno');

// Array para almacenar los turnos
var listadoTurnos = [];

// Función para agregar un nuevo turno
function agregarTurno(event) {
    event.preventDefault(); // Evitar el envío del formulario

    // Obtener los valores del formulario
    var fecha = document.getElementById('fechaTurno').value;
    var hora = document.getElementById('horaTurno').value;

    // Crear un objeto turno
    var turno = {
        fecha: fecha,
        hora: hora
    };

    // Agregar el turno al listado
    listadoTurnos.push(turno);

    // Limpiar los campos del formulario
    document.getElementById('fechaTurno').value = '';
    document.getElementById('horaTurno').value = '';

    console.log('Nuevo turno agregado:', turno);
    console.log('Listado de turnos:', listadoTurnos);
}

// Asignar función al botón "Nuevo Turno"
nuevoTurnoBtn.addEventListener('click', function () {
    formAgregarTurno.style.display = 'block';
});

// Asignar función al formulario de agregar turno
formAgregarTurno.addEventListener('submit', agregarTurno);



});

*/