package com.longdrink.androidapp.api

import com.longdrink.androidapp.model.Curso
import com.longdrink.androidapp.model.CursoResponse
import com.longdrink.androidapp.model.LoginSendData
import com.longdrink.androidapp.model.LoginWebResponse
import com.longdrink.androidapp.model.RegisterResponse
import com.longdrink.androidapp.model.RegisterSendData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    /** ---------------------------- AUTH METHODS --------------------------*/
    @POST("auth/iniciar_sesion")
    suspend fun IniciarSesion(@Body login: LoginSendData) : Response<LoginWebResponse>


    @POST("auth/registro")
    suspend fun RegistroAlumno(@Body registro : RegisterSendData) : Response<RegisterResponse>

    /** ---------------------------- COURSES RELATED METHODS --------------------------*/

    @GET("curso")
    suspend fun ListarCursos() : Response<List<Curso>>

    /** ---------------------------- INSCRIPTIONS RELATED METHODS --------------------------*/
    @

}