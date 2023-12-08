package com.longdrink.rest_api.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "Profesor")
@Table(name = "profesor")
public class Profesor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_profesor")
    @JsonProperty("codProfesor")
    private Long codProfesor;
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
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_contratacion")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    @JsonProperty("fechaContratacion")
    private Date fechaContratacion;
    private boolean activo;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "cod_usuario")
    private Usuario usuario;

    @JsonBackReference
    @OneToMany(mappedBy = "profesor")
    private List<Seccion> secciones;

    public Profesor(){}

    public Profesor(Long codProfesor, String nombre, String apellidoPaterno, String apellidoMaterno, String dni, String telefono, Date fechaContratacion, boolean activo, Usuario usuario, List<Curso> cursos) {
        this.codProfesor = codProfesor;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.dni = dni;
        this.telefono = telefono;
        this.fechaContratacion = fechaContratacion;
        this.activo = activo;
        this.usuario = usuario;
    }

    public Profesor(Long codProfesor, String nombre, String apellidoPaterno, String apellidoMaterno, String dni, String telefono, Date fechaContratacion, boolean activo, Usuario usuario) {
        this.codProfesor = codProfesor;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.dni = dni;
        this.telefono = telefono;
        this.fechaContratacion = fechaContratacion;
        this.activo = activo;
        this.usuario = usuario;
    }

    public Long getCodProfesor() {
        return codProfesor;
    }

    public void setCodProfesor(Long codProfesor) {
        this.codProfesor = codProfesor;
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

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
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

    public List<Seccion> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<Seccion> secciones) {
        this.secciones = secciones;
    }
}
