"use strict"

let formCarrera = document.getElementById("formCarrera");
formCarrera.addEventListener('submit', postCarrera);

let formCiudad = document.getElementById("formCiudad");
formCiudad.addEventListener('submit', postCiudad);

let formEst = document.getElementById("formEst");
formEst.addEventListener('submit', postEstudiante);

let formByLibreta = document.getElementById("formByLibreta");
formByLibreta.addEventListener('submit', imprimirEstudianteByLibreta);

let formGenero = document.getElementById("formGenero");
formGenero.addEventListener('submit', imprimirEstudianteGenero);

let formEstuduanteCiudadCarrera = document.getElementById("formEstuduanteCiudadCarrera");
formEstuduanteCiudadCarrera.addEventListener('submit', imprimirEstudianteCiudadCarrera);

let formMatricular = document.getElementById("formMatricular");
formMatricular.addEventListener('submit', matricular);

let getCarrerasCantidad = document.getElementById("getCarrerasCantidad");
getCarrerasCantidad.addEventListener('click', carrerasCantidad);

let getInforme = document.getElementById("getInforme");
getInforme.addEventListener('click', informe);


document.addEventListener('DOMContentLoaded', iniciar);
function iniciar(){
  getCarreras();
  setTimeout(() => {
    getCiudades();
  }, "3000"); 
  setTimeout(() => {
    getEstudiantesByEdad();
  }, "6000");
}

function date(){
  const date = new Date();
  const año = date.getFullYear();
  const mes = date.getMonth()+1>9? date.getMonth()+1 : '0'+(date.getMonth()+1);
  const dia = date.getDate()>9? date.getDate() : '0'+date.getDate();
  const horas = date.getHours()>9? date.getHours() : '0'+date.getHours();
  const minutos = date.getMinutes()>9? date.getMinutes() : '0'+date.getMinutes();
  const milisegundos = '0.0';
  const fecha = año+'-'+mes+'-'+dia+' '+horas+':'+minutos+':'+milisegundos;
  return fecha;
}

function informe(){
  event.preventDefault();
  const date = new Date();
  const año = date.getFullYear();
  const mes = date.getMonth()+1>9? date.getMonth()+1 : '0'+(date.getMonth()+1);
  const dia = date.getDate()>9? date.getDate() : '0'+date.getDate();
  const fecha = dia+'/'+mes+'/'+año;
  let tbody6 = document.getElementById("tbody6");
  tbody6.innerHTML = "";
  const url = 'http://localhost:8080/adaptandoTP/rest/inscriptos/info_carrera?date='+fecha;
  fetch(url)
  .then(response => response.json())
  .then(data => {
    data.forEach(e =>{
      const graduado = e.graduado? 'Si' : 'No';
      tbody6.innerHTML += 
      "<tr><td>" + e.nombreCarrera + "</td><td>" + e.nombreEstudiante + "</td><td> " + e.apellidoEstudiante + "</td><td>" + graduado + "</td><td>" + e.antiguedad + "</td></tr>";
    });
  });
}

function carrerasCantidad(){
  event.preventDefault();
  let tbody4 = document.getElementById("tbody4");
  tbody4.innerHTML = "";
  let i = 1;
  const url = 'http://localhost:8080/adaptandoTP/rest/inscriptos/carreras_con_inscriptos';
  fetch(url)
  .then(response => response.json())
  .then(data => {
    data.forEach(c =>{
      tbody4.innerHTML += 
      "<tr><td>" + i + "</td><td>" + c.nombre + "</td></tr>";
      i++;
    });
  }); 
}

function imprimirEstudianteCiudadCarrera(){
  event.preventDefault();
  let carrera = formEstuduanteCiudadCarrera.querySelector('select[name="carrera"]').value;
  let ciudad = formEstuduanteCiudadCarrera.querySelector('select[name="ciudad"]').value;
  let tbody5 = document.getElementById("tbody5");
  tbody5.innerHTML = "";
  const url = 'http://localhost:8080/adaptandoTP/rest/inscriptos/estudiante_por_carrera?carrera='+carrera+'&ciudad='+ciudad;
  fetch(url)
  .then(response => response.json())
  .then(data => {
    data.forEach(e =>{
      tbody5.innerHTML += 
      "<tr><td>" + e.nombre + "</td><td>" + e.apellido + "</td><td> " + e.edad + "</td><td>" + e.dni + "</td><td>" + e.ciudad.nombre +
      "</td><td>" + e.genero + "</td><td>" + e.numLibretaUniversitaria + "</td></tr>";
    });
  }); 
}

function imprimirEstudianteGenero(){
  event.preventDefault();
  let genero = formGenero.querySelector('select[name="genero"]').value;
  let tbody3 = document.getElementById("tbody3");
  tbody3.innerHTML = "";
  const url = 'http://localhost:8080/adaptandoTP/rest/estudiantes/genero?genero='+genero;
  fetch(url)
  .then(response => response.json())
  .then(data => {
    data.forEach(e =>{
      tbody3.innerHTML += 
      "<tr><td>" + e.nombre + "</td><td>" + e.apellido + "</td><td> " + e.edad + "</td><td>" + e.dni + "</td><td>" + e.ciudad.nombre +
      "</td><td>" + e.genero + "</td><td>" + e.numLibretaUniversitaria + "</td></tr>";
    });
  }); 
}

function imprimirEstudianteByLibreta(){
  event.preventDefault();
  let libreta = formByLibreta.querySelector('input[name="libreta"]').value;
  getEstudiantesByLibreta(libreta);
}

function getEstudiantesByLibreta(libreta){
  let tbody2 = document.getElementById("tbody2");
  tbody2.innerHTML = "";
  const url = 'http://localhost:8080/adaptandoTP/rest/estudiantes/libreta?from='+libreta;
  fetch(url)
  .then(response => response.json())
  .then(e => {
    tbody2.innerHTML += 
    "<tr><td>" + e.nombre + "</td><td>" + e.apellido + "</td><td> " + e.edad + "</td><td>" + e.dni + "</td><td>" + e.ciudad.nombre +
    "</td><td>" + e.genero + "</td><td>" + e.numLibretaUniversitaria + "</td></tr>";
  }); 
}

function getEstudiantesByEdad(){
  fetch('http://localhost:8080/adaptandoTP/rest/estudiantes/estudiantesByEdad')
  .then(response => response.json())
  .then(data => listarEstudiantes(data))
  .catch(() => getEstudiantesByEdad()); // Capturar errores
}

function listarEstudiantes(data){
  let body = document.getElementById("tbody");
  data.forEach(e=>{
    body.innerHTML += 
    "<tr><td>" + e.nombre + "</td><td>" + e.apellido + "</td><td> " + e.edad + "</td><td>" + e.dni + "</td><td>" + e.ciudad.nombre +
    "</td><td>" + e.genero + "</td><td>" + e.numLibretaUniversitaria + "</td></tr>";
  });
}

function getCiudades(){
  fetch('http://localhost:8080/adaptandoTP/rest/ciudades')
  .then(response => response.json())
  .then(data => addCiudades(data))
  .catch(() => getCiudades()); // Capturar errores
}

function addCiudades(data){
  let select = document.getElementById("selectCiudades");
  let select2 = document.getElementById("selectCiudades2");
  select.innerHTML = '';
  select2.innerHTML = '';
  data.forEach(d =>{
    select.innerHTML += "<option value=" + d.id +"/"+ d.nombre +">"+ d.nombre +"</option>";
    select2.innerHTML += "<option value=" + d.nombre +">"+ d.nombre +"</option>";
  });
}


function getCarreras(){
  fetch('http://localhost:8080/adaptandoTP/rest/carreras')
  .then(response => response.json())
  .then(data => addCarreras(data))
  .catch(() => getCarreras()); // Capturar errores
}

function addCarreras(data){
  let select = document.getElementById("selectCarreras");
  let select2 = document.getElementById("selectCarreras2");
  select.innerHTML = '';
  select2.innerHTML = '';
  data.forEach(d =>{
    select.innerHTML += "<option value=" + d.id +"/"+ d.nombre +">"+ d.nombre +"</option>";
    select2.innerHTML += "<option value=" + d.nombre +">"+ d.nombre +"</option>";
  });
}

function postCarrera() {
  event.preventDefault();
  let value = formCarrera.querySelector('input[name="nameCarrera"]').value;
  formCarrera.querySelector('input[name="nameCarrera"]').value = "";
  (async () => {
    const rawResponse = await fetch('http://localhost:8080/adaptandoTP/rest/carreras', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({"nombre": value})
    });
    const content = await rawResponse.json();
    if(content){
      document.getElementById("postCarrera").innerHTML ="<h5 class='text-success'>Carrera Creada</h5>";
      getCarreras();
    }
  })();
}

function postCiudad() {
  event.preventDefault();
  let value = formCiudad.querySelector('input[name="nameCiudad"]').value;
  formCiudad.querySelector('input[name="nameCiudad"]').value = "";
  (async () => {
    const rawResponse = await fetch('http://localhost:8080/adaptandoTP/rest/ciudades', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({"nombre": value})
    });
    const content = await rawResponse.json();
    if(content){
      document.getElementById("postCiudad").innerHTML ="<h5 class='text-success'>Ciudad Creada</h5>";
      getCiudades();
    }
  })();
}

function matricular(){
  event.preventDefault();
  let m = document.getElementById("divMatricular");
  m.innerHTML = "";
  let libreta = formMatricular.querySelector('input[name="libreta"]').value;
  let carrera = formMatricular.querySelector('select[name="carreras"]').value;
  let graduado = formMatricular.querySelector('input[name="graduado"]').checked;
  const url = 'http://localhost:8080/adaptandoTP/rest/estudiantes/libreta?from='+libreta;
  carrera = carrera.split("/");
  fetch(url)
  .then(response => response.json())
  .then(e => {
    postInscripto2(e,carrera,graduado);
  })
  .catch(function() {
    m.innerHTML += "<h5 class='text-danger'>El estudiante no existe</h5>"
  });
}

function postInscripto2(e,carrera,graduado){
  let g = graduado? 1 : 0;
  let fecha = date();
  (async () => {
    const rawResponse = await fetch('http://localhost:8080/adaptandoTP/rest/inscriptos', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        "estudiante":{
          "id":e.id,
          "nombre" : e.nombre,
          "apellido" : e.apellido,
          "edad" : e.edad,
          "genero": e.genero,
          "dni":e.dni,
          "numLibretaUniversitaria":e.numLibretaUniversitaria,
          "ciudad": {"id":e.ciudad.id,"nombre":e.ciudad.nombre}
        },
        "carrera":{"id":carrera[0],"nombre":carrera[1]},
        "antiguedad": fecha,
        "graduado": g
      })
    });
    const content = await rawResponse.json();
  })();
}


function postEstudiante() {
  event.preventDefault();
  let nombre = formEst.querySelector('input[name="nombre"]').value;
  let apellido = formEst.querySelector('input[name="apellido"]').value;
  let edad = formEst.querySelector('input[name="edad"]').value;
  let genero = formEst.querySelector('input[name="genero"]').value;
  let dni = formEst.querySelector('input[name="dni"]').value;
  let libreta = formEst.querySelector('input[name="libreta"]').value;
  let ciudad = formEst.querySelector('select[name="ciudad"]').value;
  ciudad = ciudad.split("/");
  (async () => {
    const rawResponse = await fetch('http://localhost:8080/adaptandoTP/rest/estudiantes', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({"nombre": nombre,
        "apellido": apellido,
        "edad": edad,
        "genero": genero,
        "dni": dni,
        "numLibretaUniversitaria": libreta,
        "ciudad": {"id":ciudad[0],"nombre":ciudad[1]},
      })
    });
    const content = await rawResponse.json();
  })();
}


      