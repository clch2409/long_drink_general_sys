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

data class CursoResponse(
    val listadoCursos : List<Curso>
)

data class Curso(
    @SerializedName("codCurso") var codCurso : Long = 0L,
    @SerializedName("descripcion") var descripcion : String = "",
    @SerializedName("mensualidad") var mensualidad : Double = 0.0,
    @SerializedName("duracion") var duracion : Byte = 0,
    @SerializedName("cantidadAlumnos") var cantidadAlumnos : Byte = 0,
    @SerializedName("visibilidad") var visibilidad : Boolean = false,
    @SerializedName("frecuencia") var frecuencia : String = "",
    @SerializedName("imagen") var imagen : String = "",

)
