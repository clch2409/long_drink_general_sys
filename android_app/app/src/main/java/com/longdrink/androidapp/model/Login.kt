package com.longdrink.androidapp.model

class Login {

    private lateinit var nombreUsuario : String;
    private lateinit var contrasena : String

    constructor(nombreUsuario: String, contrasena: String) {
        this.nombreUsuario = nombreUsuario
        this.contrasena = contrasena
    }
}