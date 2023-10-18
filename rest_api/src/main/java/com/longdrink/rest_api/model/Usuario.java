package com.longdrink.rest_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity(name = "Usuario")
@Table(name = "usuario")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_usuario")
    @JsonProperty("codUsuario")
    private Long codUsuario;

    @Column(name = "nombre_usuario",length = 50)
    @JsonProperty("nombreUsuario")
    private String nombreUsuario;

    private String contrasena;

    @Column(length = 50)
    private String email;

    private boolean activo;

    @ManyToOne()
    @JoinColumn(name = "cod_rol")
    private Rol rol;

    public Usuario(){}

    public Usuario(Long codUsuario, String nombreUsuario, String contrasena, String email, boolean activo, Rol rol) {
        this.codUsuario = codUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.email = email;
        this.activo = activo;
        this.rol = rol;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
