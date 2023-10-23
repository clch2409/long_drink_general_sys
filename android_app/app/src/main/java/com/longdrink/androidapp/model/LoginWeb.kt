package com.longdrink.androidapp.model

class LoginWeb {

    private lateinit var nombreUsuario : String
    private lateinit var contrasena : String
    private lateinit var email : String
    private lateinit var nombreCompleto : String
    private lateinit var rol : String

    constructor(nombreUsuario: String, contrasena: String, email: String, nombreCompleto: String, rol: String) {
        this.nombreUsuario = nombreUsuario
        this.contrasena = contrasena
        this.email = email
        this.nombreCompleto = nombreCompleto
        this.rol = rol
    }
}