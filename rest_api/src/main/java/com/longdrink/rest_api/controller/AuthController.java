package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.Alumno;
import com.longdrink.rest_api.model.Profesor;
import com.longdrink.rest_api.model.Rol;
import com.longdrink.rest_api.model.Usuario;
import com.longdrink.rest_api.model.payload.Login;
import com.longdrink.rest_api.model.payload.LoginWeb;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.model.payload.RegistroAlumno;
import com.longdrink.rest_api.services.AlumnoService;
import com.longdrink.rest_api.services.ProfesorService;
import com.longdrink.rest_api.services.UsuarioService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AlumnoService alumnoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/registro")
    public ResponseEntity<?> registroAlumno(@RequestBody RegistroAlumno reg){
        EmailValidator em = EmailValidator.getInstance();
        Alumno a = alumnoService.getPorDNI(reg.getDni());
        Profesor p = profesorService.getPorDNI(reg.getDni());
        Usuario u = usuarioService.getPorEmail(reg.getEmail());
        if(a != null || p != null){
            return new ResponseEntity<>(new Mensaje("Error! El DNI ingresado ya pertenece a una cuenta registrada.",400),
                    HttpStatus.BAD_REQUEST);
        }
        if(u != null){
            return new ResponseEntity<>(new Mensaje("Error! El E-Mail ingresado ya pertenece a una cuenta registrada.",400),
                    HttpStatus.BAD_REQUEST);
        }
        RegistroAlumno limpiarDatos = reg.limpiarDatos();
        limpiarDatos.setNombreUsuario(limpiarDatos.generarNombreUsuario());
        limpiarDatos.setContrasena(bCryptPasswordEncoder.encode(limpiarDatos.getContrasena()));
        boolean datosValidos = limpiarDatos.validarDatos();
        boolean emailValido = em.isValid(limpiarDatos.getEmail());
        if(!datosValidos || !emailValido){
            return new ResponseEntity<>(new Mensaje("Error! Los datos ingresados no cuentan con el formato requerido.",400),
                    HttpStatus.BAD_REQUEST);
        }
        //Insert de datos.
        try{
            Rol r = new Rol(3L,"ALUMNO");
            Usuario usuario = new Usuario(0L,limpiarDatos.getNombreUsuario(),
                    limpiarDatos.getContrasena(),limpiarDatos.getEmail(),true,r);
            Usuario usuarioGuardado = usuarioService.guardar(usuario);
            Alumno alumno = new Alumno(0L,limpiarDatos.getNombre(),limpiarDatos.getApellidoPaterno(),
                    limpiarDatos.getApellidoMaterno(),limpiarDatos.getDni(),
                    limpiarDatos.getTelefono(),true,usuarioGuardado);
            Alumno alumnoGuardado = alumnoService.guardar(alumno);
            return new ResponseEntity<>(new Mensaje("Exito! Alumno registrado correctamente.",201),HttpStatus.CREATED);
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Error! Ha sucedido en error en el guardado de datos.",500),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/iniciar_sesion")
    public ResponseEntity<?> iniciarSesion(@RequestBody Login l){
        Usuario u = usuarioService.getPorUsername(l.getNombreUsuario().trim().toUpperCase());
        if(u == null){
            return new ResponseEntity<>(new Mensaje("Error! Credenciales de acceso incorrectas.",401),
                    HttpStatus.UNAUTHORIZED);
        }
        boolean login = bCryptPasswordEncoder.matches(l.getContrasena(),u.getContrasena());
        if(!login){
            return new ResponseEntity<>(new Mensaje("Error! Credenciales de acceso incorrectas.",401),
                    HttpStatus.UNAUTHORIZED);
        }
        if(u.getRol().getCodRol() == 3L){
            Alumno a = alumnoService.getPorCodUsuario(u.getCodUsuario());
            String nombreCompleto = a.getNombre()+" "+a.getApellidoPaterno()+" "+a.getApellidoMaterno();
            LoginWeb respuesta = new LoginWeb(u.getNombreUsuario(),"<-CONTRASEÑA->",u.getEmail(),nombreCompleto,"ALUMNO");
            return new ResponseEntity<>(respuesta,HttpStatus.OK);
        }
        if(u.getRol().getCodRol() == 2L){
            Profesor p = profesorService.getPorCodUsuario(u.getCodUsuario());
            String nombreCompleto = p.getNombre() +" "+p.getApellidoPaterno()+" "+p.getApellidoMaterno();
            LoginWeb respuesta = new LoginWeb(u.getNombreUsuario(),"<-CONTRASEÑA->",u.getEmail(),nombreCompleto,"DOCENTE");
            return new ResponseEntity<>(respuesta,HttpStatus.OK);
        }
        if(u.getRol().getCodRol() == 1L){
            LoginWeb respuesta = new LoginWeb(u.getNombreUsuario(),"<-CONTRASEÑA->",u.getEmail(),"Gerencia Long Drink","ADMINISTRADOR");
            return new ResponseEntity<>(respuesta,HttpStatus.OK);
        }
        return new ResponseEntity<>(new Mensaje("Error! Credenciales de acceso incorrectas.",401),
                HttpStatus.UNAUTHORIZED);
    }
}
