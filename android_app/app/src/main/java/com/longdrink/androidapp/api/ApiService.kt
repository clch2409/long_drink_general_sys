package com.longdrink.androidapp.api

import com.longdrink.androidapp.model.LoginSendData
import com.longdrink.androidapp.model.LoginWebResponse
import com.longdrink.androidapp.model.RegisterResponse
import com.longdrink.androidapp.model.RegisterSendData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/iniciar_sesion")
    suspend fun IniciarSesion(@Body login: LoginSendData) : Response<LoginWebResponse>


    @POST("auth/registro")
    suspend fun RegistroAlumno(@Body registro : RegisterSendData) : Response<RegisterResponse>
}