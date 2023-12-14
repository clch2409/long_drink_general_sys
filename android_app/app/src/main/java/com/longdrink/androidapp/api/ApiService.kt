package com.longdrink.androidapp.api

import com.longdrink.androidapp.model.Alumno
import com.longdrink.androidapp.model.Asistencia
import com.longdrink.androidapp.model.CambiarCredenciales
import com.longdrink.androidapp.model.Curso
import com.longdrink.androidapp.model.Inscripcion
import com.longdrink.androidapp.model.ListItemCursoTerminado
import com.longdrink.androidapp.model.LoginSendData
import com.longdrink.androidapp.model.LoginWebResponse
import com.longdrink.androidapp.model.MarcarAsistencia
import com.longdrink.androidapp.model.Mensaje
import com.longdrink.androidapp.model.Pago
import com.longdrink.androidapp.model.RegisterResponse
import com.longdrink.androidapp.model.RegisterSendData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface ApiService {
    /** ---------------------------- AUTH METHODS --------------------------*/
    @POST("auth/iniciar_sesion")
    suspend fun iniciarSesion(@Body login: LoginSendData) : Response<LoginWebResponse>


    @POST("auth/registro")
    suspend fun registroAlumno(@Body registro : RegisterSendData) : Response<RegisterResponse>

    @POST("auth/cambiar_credenciales")
    suspend fun cambiarCredenciales(@Body cambiarCredenciales : CambiarCredenciales) : Response<Mensaje>

    @POST("auth/recuperar_cuenta")
    suspend fun recuperarCredenciales(@Query("email") email : String) : Response<ResponseBody>

    /** ---------------------------- COURSES RELATED METHODS --------------------------*/

    @GET("curso")
    suspend fun listarCursos() : Response<List<ListItemCursoTerminado>>

    @GET("curso/{id}")
    suspend fun findCursoById(@Path("id") codCurso : Long) : Response<Curso>

    /** ---------------------------- INSCRIPTIONS RELATED METHODS --------------------------*/
    @GET("inscripcion/por_alumno")
    suspend fun listarInscripcionesByAlumnoId(@Query("codAlumno") codAlumno : Long) : Response<List<Inscripcion>>

    @GET("inscripcion/por_curso")
    suspend fun listarInscripcionesByCursoId(@Query("codCurso") codCurso : Long) : Response<List<Inscripcion>>

    @GET("inscripcion/por_cod")
    suspend fun buscarInscripcionPorCod(@Query("codInscripcion") codInscripcion : Long) : Response<Inscripcion>

    /*@POST("inscripcion")
    suspend fun realizarInscripcion(@Body inscripcion : InscripcionDetallada) : Response<Inscripcion>*/

    /**----------------------------------------DOWNLOAD STUDY GUIDE------------------------------*/
    @Streaming
    @GET("tema/descargar_guia")
    suspend fun descargarGuia(@Query("codTema") codTema : Long) : Response<ResponseBody>

    /**----------------------------------------PAYMENTS RELATED METHODS------------------------------*/
    @GET("pago")
    suspend fun listarPagosAlumno(@Query("codAlumno") codAlumno : Long) : Response<List<Pago>>

    /**----------------------------------------STUDENTS RELATED METHODS------------------------------*/
    @GET("alumno/por_cod")
    suspend fun buscarAlumnoPorID(@Query("codAlumno") codAlumno : Long) : Response<Alumno>

    /**----------------------------------------STUDENTS RELATED METHODS------------------------------*/
    @POST("asistencia")
    suspend fun marcarAsistencia(@Body marcarAsistencia: MarcarAsistencia): Response<Asistencia>

    @GET("asistencia/comprobar")
    suspend fun comprobarAsistencia(@Query("fechaAsistencia") fechaAsistencia : String, @Query("codInscripcion") codInscripcion : Long)
    : Response<Mensaje>
}