package com.longdrink.androidapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date

/** --------------------- LOGIN AND REGISTER RELATED DATA CLASSES ----------------------------------*/
data class LoginWebResponse(
    @SerializedName("codUsuario") val codUsuario : Long,
    @SerializedName("codAlumno") val codAlumno : Long,
    @SerializedName("nombreUsuario") val nombreUsuario : String,
    @SerializedName("contrasena") val contrasena : String,
    @SerializedName("email") val email : String,
    @SerializedName("nombreCompleto") val nombreCompleto : String,
    @SerializedName("rol") val rol : String
) : Serializable

data class LoginSendData(
    @SerializedName("nombreUsuario") val nombreUsuario : String,
    @SerializedName("contrasena") val contrasena : String
) : Serializable

data class RegisterSendData(
    @SerializedName("nombre") val nombre : String,
    @SerializedName("apellidoPaterno") val apPaterno : String,
    @SerializedName("apellidoMaterno") val apMaterno : String,
    @SerializedName("dni") val dni : String,
    @SerializedName("telefono") val telefono : String,
    @SerializedName("email") val email : String,
    @SerializedName("contrasena") var contrasena : String
) : Serializable

data class RegisterResponse(
    @SerializedName("codAlumno") val codAlumno : Long,
    @SerializedName("codUsuario") val codUsuario : Long,
    @SerializedName("nombreUsuario") val nombreUsuario : String,
    @SerializedName("email") val email : String,
    @SerializedName("nombre") val nombre : String,
    @SerializedName("apellidoPaterno") val apellidoPaterno : String,
    @SerializedName("apellidoMaterno") val apellidoMaterno : String,
    @SerializedName("dni") val dni : String,
) : Serializable

data class CambiarCredenciales(
    @SerializedName("contrasenaAntigua") val contrasenaAntigua : String,
    @SerializedName("nuevaContrasena") val nuevaContrasena : String,
    @SerializedName("emailAntiguo") val emailAntiguo : String,
    @SerializedName("emailNuevo") val emailNuevo : String,
) : Serializable

data class Mensaje(
    @SerializedName("mensaje") val mensaje : String,
    @SerializedName("estado") val estado : Integer
)

/** --------------------- COURSES RELATED DATA CLASSES ----------------------------------*/
data class Curso(
    @SerializedName("codCurso") var codCurso : Long = 0L,
    @SerializedName("nombre") var nombre : String = "",
    @SerializedName("descripcion") var descripcion : String = "",
    @SerializedName("mensualidad") var mensualidad : Double = 0.0,
    @SerializedName("duracion") var duracion : Byte = 0,
    @SerializedName("cantidadAlumnos") var cantidadAlumnos : Byte = 0,
    @SerializedName("visibilidad") var visibilidad : Boolean = false,
    @SerializedName("frecuencia") var frecuencia : String = "",
    @SerializedName("imagen") var imagen : String = "",
    @SerializedName("turnos") var turnos : List<Turno> = emptyList(),
    @SerializedName("temas") var temas : List<Tema> = emptyList(),
) : Serializable

data class ListItemCursoTerminado (
    @SerializedName("codCurso") var codCurso : Long = 0L,
    @SerializedName("nombre") var nombre : String = "",
    @SerializedName("descripcion") var descripcion : String = "",
    @SerializedName("mensualidad") var mensualidad : Double = 0.0,
    @SerializedName("duracion") var duracion : Byte = 0,
    @SerializedName("cantidadAlumnos") var cantidadAlumnos : Byte = 0,
    @SerializedName("visibilidad") var visibilidad : Boolean = false,
    @SerializedName("frecuencia") var frecuencia : String = "",
    @SerializedName("imagen") var imagen : String = "",
    @SerializedName("profesor") var profesor : Profesor = Profesor(),
    @SerializedName("turnos") var turnos : List<Turno> = emptyList(),
    @SerializedName("temas") var temas : List<Tema> = emptyList(),
    var fechaInicioInscripcion : String?,
    var fechaTerminadoInscripcion : String?,
    var fechaFinalInscripcion : String?,
    var fechaInscripcion : String?,
    var estadoInscripcion : Boolean?
) : Serializable

/** --------------------- TEACHER RELATED DATA CLASSES ----------------------------------*/

data class Profesor(
    @SerializedName("codProfesor") var codProfesor : Long = 0L,
    @SerializedName("nombre") var nombre : String = "",
    @SerializedName("dni") var dni : String = "",
    @SerializedName("telefono") var telefono : String = "",
    @SerializedName("activo") var activo : Boolean = false,
    @SerializedName("apellidoPaterno") var apellidoPaterno : String = "",
    @SerializedName("apellidoMaterno") var apellidoMaterno : String = "",
    @SerializedName("fechaContratacion") var fechaContratacion : String = "",
) : Serializable

/** --------------------- TURN DATA CLASSES ----------------------------------*/

data class Turno(
    @SerializedName("codTurno") var codTurno : Long = 0,
    @SerializedName("nombre") var nombre : String = "",
    @SerializedName("horaInicio") var horaInicio : String = "",
    @SerializedName("horaFin") var horaFin : String = ""
) : Serializable

/** --------------------- CLASS DATA CLASSES ----------------------------------*/

data class Tema(
    @SerializedName("codTema") var codTema : Long,
    @SerializedName("nombre") var nombre : String,
    @SerializedName("ficha") var ficha : String
) : Serializable

/** --------------------- INSCRIPTION RELATED DATA CLASSES ----------------------------------*/

data class Inscripcion(
    @SerializedName("codInscripcion") var codInscripcion : Long = 0L,
    @SerializedName("fechaInscripcion") var fechaInscripcion: String = "",
    @SerializedName("fechaTerminado") var fechaTerminado: String = "",
    @SerializedName("estado") var estado: Boolean = false,
    @SerializedName("alumno") var codAlum: Long,
    @SerializedName("asistencias") var asistencias: List<Asistencia>,
    @SerializedName("seccion") var seccion: Seccion,


) : Serializable

data class Asistencia(
    @SerializedName("codAsistencia") var codAsistencia: Long = 0L,
    @SerializedName("fechaAsistencia") var fechaAsistencia: String,
    @SerializedName("horaLlegada") var horaLlegada: String,
    @SerializedName("estado") var estado: Int = 0,
    @SerializedName("inscripcion") var inscripcion: Inscripcion
) : Serializable

data class MarcarAsistencia(
    @SerializedName("codInscripcion") var codInscripcion: Long = 0L,
    @SerializedName("fechaAsistencia") var fechaAsistencia : String,
    @SerializedName("horaLlegada") var horaLlegada: String,
    @SerializedName("estado") var estado: Boolean,

) : Serializable

/** --------------------- PAYMENTS RELATED DATA CLASSES ----------------------------------*/
data class Pago(
    @SerializedName("codPago") var codPago : Long = 0L,
    @SerializedName("fechaPago") var fechaPago : String = "",
    @SerializedName("fechaVencimiento") var fechaVencimiento : String,
    @SerializedName("estado") var estado : Boolean,
    @SerializedName("descripcion") var descripcion : String,
    @SerializedName("total") var total : Double
) : Serializable
/** --------------------- SECTION RELATED DATA CLASSES ----------------------------------*/
data class Seccion(
    @SerializedName("codSeccion") var codSeccion : Long = 0L,
    @SerializedName("nombre") var nombre : String = "",
    @SerializedName("fechaInicio") var fechaInicio : String,
    @SerializedName("fechaFinal") var fechaFinal : String,
    @SerializedName("estado") var estado : Boolean,
    @SerializedName("maxAlumnos") var maxAlumnos : Integer,
    @SerializedName("curso") var curso : Curso,
    @SerializedName("turno") var turno : Turno,
    @SerializedName("profesor") var profesor : Profesor,
) : Serializable

data class Alumno(
    @SerializedName("codAlumno") var codAlumno : Long = 0L,
    @SerializedName("nombre") var nombre: String = "",
    @SerializedName("apellidoPaterno") var apellidoPaterno: String = "",
    @SerializedName("apellidoMaterno") var apellidoMaterno: String = "",
    @SerializedName("dni") var dni : String = "",
    @SerializedName("telefono") var telefono: String = "",
    @SerializedName("activo") var activo: Boolean = false,
    @SerializedName("inscripciones") var codInscripciones : List<Long> = emptyList(),
    @SerializedName("pagos") var pagos : List<Pago> = emptyList(),

    )