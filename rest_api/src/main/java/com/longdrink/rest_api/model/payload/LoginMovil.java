package com.longdrink.rest_api.model.payload;

public class LoginMovil {
    private Long codAlumno;
    private Long codUsuario;
    private String nombreUsuario;
    private String contrasena;
    private String email;
    private String nombreCompleto;
    private String rol;

    public LoginMovil(){}

    public LoginMovil(Long codAlumno, Long codUsuario, String nombreUsuario, String contrasena, String email, String nombreCompleto, String rol) {
        this.codAlumno = codAlumno;
        this.codUsuario = codUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

    public Long getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(Long codAlumno) {
        this.codAlumno = codAlumno;
    }

    public Long getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Long codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
