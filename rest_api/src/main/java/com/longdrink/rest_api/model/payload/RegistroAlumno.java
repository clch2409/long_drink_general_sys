package com.longdrink.rest_api.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistroAlumno {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String dni;
    private String telefono;
    @JsonIgnore
    private String nombreUsuario;
    private String email;
    private String contrasena;

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
            return new RegistroAlumno(
                    this.nombre.trim().toUpperCase(),this.apellidoPaterno.trim().toUpperCase(),
                    this.apellidoMaterno.trim().toUpperCase(),this.dni.trim(),this.telefono.trim(),
                    this.generarNombreUsuario(),this.email.trim().toUpperCase(),this.contrasena
            );
        }
        catch(Exception ex){ return null; }
    }

    @JsonIgnore
    public boolean validarDatos(){
        return this.nombre.length() >= 1 && this.nombre.length() <= 50 &&
                this.apellidoPaterno.length() >= 1 && this.apellidoPaterno.length() <= 25 &&
                this.apellidoMaterno.length() >= 1 && this.apellidoMaterno.length() <= 25 &&
                this.dni.length() >= 8 && this.dni.length() <= 12 && this.telefono.length() >= 9 &&
                this.telefono.length() <= 15 && this.nombreUsuario.length() <= 50 && this.email.length() <= 50;
    }

}
