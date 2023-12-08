package com.longdrink.rest_api.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "Alumno")
@Table(name = "alumno")
public class Alumno implements Serializable {
    @Id
    @Column(name = "cod_alumno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("codAlumno")
    private Long codAlumno;
    @Column(length = 50)
    private String nombre;
    @Column(name = "apellido_paterno",length = 25)
    @JsonProperty("apellidoPaterno")
    private String apellidoPaterno;
    @Column(name = "apellido_materno",length = 25)
    @JsonProperty("apellidoMaterno")
    private String apellidoMaterno;
    @Column(length = 12)
    private String dni;
    @Column(length = 15)
    private String telefono;
    private boolean activo;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "cod_usuario")
    private Usuario usuario;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codInscripcion")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "alumno")
    private List<Inscripcion> inscripciones;

    @OneToMany(mappedBy = "alumno")
    private List<Pago> pagos;

    public Alumno(){}

    public Alumno(Long codAlumno){
        this.codAlumno = codAlumno;
    }

    public Alumno(Long codAlumno, String nombre, String apellidoPaterno, String apellidoMaterno, String dni, String telefono, boolean activo, Usuario usuario, List<Inscripcion> inscripciones, List<Pago> pagos) {
        this.codAlumno = codAlumno;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.dni = dni;
        this.telefono = telefono;
        this.activo = activo;
        this.usuario = usuario;
        this.inscripciones = inscripciones;
        this.pagos = pagos;
    }

    public Alumno(Long codAlumno, String nombre, String apellidoPaterno, String apellidoMaterno, String dni, String telefono, boolean activo, Usuario usuario) {
        this.codAlumno = codAlumno;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.dni = dni;
        this.telefono = telefono;
        this.activo = activo;
        this.usuario = usuario;
    }

    public Long getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(Long codAlumno) {
        this.codAlumno = codAlumno;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }
}
