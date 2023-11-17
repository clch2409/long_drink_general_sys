package com.longdrink.rest_api.model.payload;

public class LoginWebProfesor {
    private Long codProfesor;
    private String nombreUsuario;
    private String contrasena;
    private String email;
    private String nombreCompleto;
    private String rol;

    public LoginWebProfesor(){}

    public LoginWebProfesor(Long codProfesor, String nombreUsuario, String contrasena, String email, String nombreCompleto, String rol) {
        this.codProfesor = codProfesor;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

    public Long getCodProfesor(){
      return this.codProfesor;
    }

    public void setCodProfesor(Long codProfesor){
      this.codProfesor = codProfesor;
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
