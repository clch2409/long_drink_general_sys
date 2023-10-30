package com.longdrink.rest_api.model.payload;

public class RetornoAlumno {
    private Long codAlumno;
    private Long codUsuario;
    private String nombreUsuario;
    private String email;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String dni;

    public RetornoAlumno() {
    }

    public RetornoAlumno(Long codAlumno, Long codUsuario, String nombreUsuario, String email, String nombre, String apellidoPaterno, String apellidoMaterno, String dni) {
        this.codAlumno = codAlumno;
        this.codUsuario = codUsuario;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.dni = dni;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
