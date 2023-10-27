package com.longdrink.androidapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/** --------------------- LOGIN AND REGISTER RELATED DATA CLASSES ----------------------------------*/
data class LoginWebResponse(
    @SerializedName("nombreUsuario") val nombreUsuario : String,
    @SerializedName("contrasena") val contrasena : String,
    @SerializedName("email") val email : String,
    @SerializedName("nombreCompleto") val nombreCompleto : String,
    @SerializedName("rol") val rol : String
)

data class LoginSendData(
    @SerializedName("nombreUsuario") val nombreUsuario : String,
    @SerializedName("contrasena") val contrasena : String
)

data class RegisterSendData(
    @SerializedName("nombre") val nombre : String,
    @SerializedName("apellidoPaterno") val apPaterno : String,
    @SerializedName("apellidoMaterno") val apMaterno : String,
    @SerializedName("dni") val dni : String,
    @SerializedName("telefono") val telefono : String,
    @SerializedName("email") val email : String,
    @SerializedName("contrasena") var contrasena : String
)

data class RegisterResponse(
    @SerializedName("mensaje") val mensaje : String,
    @SerializedName("estado") val estado : Integer
)

/** --------------------- COURSES RELATED DATA CLASSES ----------------------------------*/
data class Curso(
    @SerializedName("codCurso") var codCurso : Long = 0L,
    @SerializedName("descripcion") var descripcion : String = "",
    @SerializedName("mensualidad") var mensualidad : Double = 0.0,
    @SerializedName("duracion") var duracion : Byte = 0,
    @SerializedName("cantidadAlumnos") var cantidadAlumnos : Byte = 0,
    @SerializedName("visibilidad") var visibilidad : Boolean = false,
    @SerializedName("frecuencia") var frecuencia : String = "",
    @SerializedName("imagen") var imagen : String = "",
    @SerializedName("profesor") var profesor : Profesor,
    @SerializedName("turnos") var turnos : List<Turno>,
    @SerializedName("temas") var temas : List<Tema>,
)
/** --------------------- TEACHER RELATED DATA CLASSES ----------------------------------*/

data class Profesor(
    @SerializedName("codProfesor") var codProfesor : Long = 0L,
    @SerializedName("nombre") var nombre : String,
    @SerializedName("dni") var dni : String,
    @SerializedName("telefono") var telefono : String,
    @SerializedName("activo") var activo : Boolean,
    @SerializedName("apellidoPaterno") var apellidoPaterno : String,
    @SerializedName("apellidoMaterno") var apellidoMaterno : String,
    @SerializedName("fechaContratacion") var fechaContratacion : String,
)

/** --------------------- TURN DATA CLASSES ----------------------------------*/

data class Turno(
    @SerializedName("codTurno") var codTurno : Long,
    @SerializedName("nombre") var nombre : String,
    @SerializedName("horaInicio") var horaInicio : String,
    @SerializedName("horaFin") var horaFin : String
)

/** --------------------- CLASS DATA CLASSES ----------------------------------*/

data class Tema(
    @SerializedName("codTema") var codTema : Long,
    @SerializedName("nombre") var nombre : String,
    @SerializedName("ficha") var ficha : String
)

/** --------------------- INSCRIPTION RELATED DATA CLASSES ----------------------------------*/

data class Inscripcion(
    @SerializedName("inscripcionPk") val inscripcionPK: InscripcionPK,
    @SerializedName("estado") val estado: Boolean,
    @SerializedName("fechaInicio") val fechaInicio: String,
    @SerializedName("fechaFinal") val fechaFinal: String,
    @SerializedName("fechaInscripcion") val fechaInscripcion: String,
    @SerializedName("fechaTerminado") val fechaTerminado: String
)

data class InscripcionPK(
    @SerializedName("codAlumno") val codAlumno : Long,
    @SerializedName("codCurso") val codCurso : Long
)
