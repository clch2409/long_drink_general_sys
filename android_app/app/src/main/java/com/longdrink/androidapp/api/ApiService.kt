package com.longdrink.androidapp.api

import com.longdrink.androidapp.model.Curso
import com.longdrink.androidapp.model.Inscripcion
import com.longdrink.androidapp.model.LoginSendData
import com.longdrink.androidapp.model.LoginWebResponse
import com.longdrink.androidapp.model.RegisterResponse
import com.longdrink.androidapp.model.RegisterSendData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /** ---------------------------- AUTH METHODS --------------------------*/
    @POST("auth/iniciar_sesion")
    suspend fun iniciarSesion(@Body login: LoginSendData) : Response<LoginWebResponse>


    @POST("auth/registro")
    suspend fun registroAlumno(@Body registro : RegisterSendData) : Response<RegisterResponse>

    /** ---------------------------- COURSES RELATED METHODS --------------------------*/

    @GET("curso")
    suspend fun listarCursos() : Response<List<Curso>>

    @GET("curso/{id}")
    suspend fun findCursoById(@Path("id") codCurso : Long) : Response<Curso>

    /** ---------------------------- INSCRIPTIONS RELATED METHODS --------------------------*/
    @GET("inscripcion/por_alumno")
    suspend fun listarInscripcionesByAlumnoId(@Query("codAlumno") codAlumno : Long) : Response<List<Inscripcion>>

}