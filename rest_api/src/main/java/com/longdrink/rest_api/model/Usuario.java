package com.longdrink.rest_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity(name = "Usuario")
@Table(name = "usuario")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_usuario")
    private Long codUsuario;
    @Column(name = "nombre_usuario",length = 50)
    private String nombreUsuario;
    private String contrasena;
    @Column(length = 50)
    private String email;
    private boolean activo;
    @OneToOne
    @JoinColumn(name = "cod_rol")
    private Rol rol;
}
