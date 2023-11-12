package com.longdrink.rest_api.model.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class RegistroAlumno {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String dni;
    private String telefono;
    @JsonIgnore
    private String nombreUsuario;
    private String email;
    @JsonIgnore
    private String contrasena;
    private Long codCurso;
    private Long codTurno;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaInicio;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaFinal;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaInscripcion;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    @JsonIgnore
    private Date fechaTerminado;

    @JsonIgnore
    public String generarNombreUsuario(){
        try{
            return this.nombre.trim().toUpperCase().substring(0,1) +
                    this.apellidoPaterno.trim().toUpperCase().substring(0,1) +
                    this.apellidoMaterno.trim().toUpperCase().substring(0,1) +
                    this.dni;
        }
        catch(Exception ex){ return ""; }
    }

    @JsonIgnore
    public RegistroAlumno limpiarDatos(){
        try{
            return new RegistroAlumno(this.nombre.trim().toUpperCase(),this.apellidoPaterno.trim().toUpperCase(),
                    this.apellidoMaterno.trim().toUpperCase(),this.dni.trim(),
                    this.telefono.trim(),this.generarNombreUsuario(),
                    this.email.trim(),this.contrasena,
                    this.codCurso,this.codTurno,
                    this.fechaInicio,this.fechaFinal,
                    this.fechaInscripcion,this.fechaTerminado);
        }
        catch(Exception ex){ return null; }
    }

    @JsonIgnore
    public boolean validarDatos(){
        return this.nombre.length() >= 1 && this.nombre.length() <= 50 &&
                this.apellidoPaterno.length() >= 1 && this.apellidoPaterno.length() <= 25 &&
                this.apellidoMaterno.length() >= 1 && this.apellidoMaterno.length() <= 25 &&
                this.dni.length() >= 8 && this.dni.length() <= 12 && this.telefono.length() >= 9 &&
                this.telefono.length() <= 15 && this.nombreUsuario.length() <= 50 && this.email.length() <= 50 &&
                this.contrasena.length() >= 5 && this.contrasena.length() <=30 && this.codCurso > 0L && this.codTurno > 0L;
    }

    public RegistroAlumno(){}

    public RegistroAlumno(String nombre, String apellidoPaterno, String apellidoMaterno, String dni, String telefono, String nombreUsuario, String email, String contrasena, Long codCurso, Long codTurno, Date fechaInicio, Date fechaFinal, Date fechaInscripcion, Date fechaTerminado) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.dni = dni;
        this.telefono = telefono;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.codCurso = codCurso;
        this.codTurno = codTurno;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaTerminado = fechaTerminado;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Long getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(Long codCurso) {
        this.codCurso = codCurso;
    }

    public Long getCodTurno() {
        return codTurno;
    }

    public void setCodTurno(Long codTurno) {
        this.codTurno = codTurno;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Date getFechaTerminado() {
        return fechaTerminado;
    }

    public void setFechaTerminado(Date fechaTerminado) {
        this.fechaTerminado = fechaTerminado;
    }
}
