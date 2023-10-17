package com.longdrink.rest_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "Profesor")
@Table(name = "profesor")
public class Profesor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_profesor")
    private Long codProfesor;
    @Column(length = 50)
    private String nombre;
    @Column(name = "apellido_paterno",length = 25)
    private String apellidoPaterno;
    @Column(name = "apellido_materno",length = 25)
    private String apellidoMaterno;
    @Column(length = 12)
    private String dni;
    @Column(length = 15)
    private String telefono;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_contratacion")
    private Date fechaContratacion;
    private boolean activo;

    @OneToOne
    @JoinColumn(name = "cod_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "profesor")
    private List<Curso> cursos;
}
