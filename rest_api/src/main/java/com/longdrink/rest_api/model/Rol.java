package com.longdrink.rest_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "Rol")
@Table(name = "rol")
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_rol")
    @JsonProperty("codRol")
    private Long codRol;

    @Column(length = 15)
    private String nombre;

    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuario;

    public Rol(){}

    public Rol(Long codRol, String nombre) {
        this.codRol = codRol;
        this.nombre = nombre;
    }

    public Long getCodRol() {
        return codRol;
    }

    public void setCodRol(Long codRol) {
        this.codRol = codRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
