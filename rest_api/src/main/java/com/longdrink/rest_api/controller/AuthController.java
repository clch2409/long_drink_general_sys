package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.*;
import com.longdrink.rest_api.model.payload.*;
import com.longdrink.rest_api.services.*;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private CursoService cursoService;
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private InscripcionService inscripcionService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired(required = true)
    private EmailService emailService;

    @PostMapping("/registro_docente")
    public ResponseEntity<?> registroDocente(@RequestBody RegistroDocente reg){
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
        RegistroDocente limpiarDatos = reg.limpiarDatos();
        limpiarDatos.setNombreUsuario(limpiarDatos.generarNombreUsuario());
        boolean datosValidos = limpiarDatos.validarDatos();
        limpiarDatos.setContrasena(bCryptPasswordEncoder.encode(limpiarDatos.getContrasena()));
        boolean emailValido = em.isValid(limpiarDatos.getEmail());
        if(!datosValidos || !emailValido){
            return new ResponseEntity<>(new Mensaje("Error! Los datos ingresados no cuentan con el formato requerido.",400),
                    HttpStatus.BAD_REQUEST);
        }
        //Insert de datos.
        try{
            Rol r = new Rol(2L,"DOCENTE");
            Usuario usuario = new Usuario(0L,limpiarDatos.getNombreUsuario(),
                    limpiarDatos.getContrasena(),limpiarDatos.getEmail(),true,r);
            Usuario usuarioGuardado = usuarioService.guardar(usuario);
            Profesor profesor = new Profesor(0L,limpiarDatos.getNombre(),limpiarDatos.getApellidoPaterno(),
                    limpiarDatos.getApellidoMaterno(),limpiarDatos.getDni(),limpiarDatos.getTelefono(),
                    limpiarDatos.getFechaContratacion(),true,usuarioGuardado);
            Profesor profesorGuardado = profesorService.guardar(profesor);
            return new ResponseEntity<>(profesorGuardado,HttpStatus.CREATED);
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Error! Ha sucedido en error en el guardado de datos.",500),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registro_admin")
    public ResponseEntity<?> registroUsuario(@RequestBody RegistroUsuario reg){
        EmailValidator em = EmailValidator.getInstance();
        Usuario u = usuarioService.getPorEmail(reg.getEmail());
        if(u != null){
            return new ResponseEntity<>(new Mensaje("Error! El E-Mail ingresado ya pertenece a una cuenta registrada.",400),
                    HttpStatus.BAD_REQUEST);
        }
        RegistroUsuario limpiarDatos = reg.limpiarDatos();
        limpiarDatos.setNombreUsuario(limpiarDatos.generarNombreUsuario());
        boolean datosValidos = limpiarDatos.validarDatos();
        limpiarDatos.setContrasena(bCryptPasswordEncoder.encode(limpiarDatos.getContrasena()));
        boolean emailValido = em.isValid(limpiarDatos.getEmail());
        if(!datosValidos || !emailValido){
            return new ResponseEntity<>(new Mensaje("Error! Los datos ingresados no cuentan con el formato requerido.",400),
                    HttpStatus.BAD_REQUEST);
        }
        //Insert de datos.
        try{
            Rol r = new Rol(1L,"ADMINISTRADOR");
            Usuario usuario = new Usuario(0L,limpiarDatos.getNombreUsuario(),
                    limpiarDatos.getContrasena(),limpiarDatos.getEmail(),true,r);
            Usuario usuarioGuardado = usuarioService.guardar(usuario);
            return new ResponseEntity<>(usuarioGuardado,HttpStatus.CREATED);
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Error! Ha sucedido un error en el guardado de datos.",500),HttpStatus.INTERNAL_SERVER_ERROR);
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
            LoginMovil respuesta = new LoginMovil(a.getCodAlumno(),u.getCodUsuario(),u.getNombreUsuario(),"<-CONTRASEÑA->",u.getEmail(),nombreCompleto,"ALUMNO");
            //LoginWeb respuesta = new LoginWeb(u.getNombreUsuario(),"<-CONTRASEÑA->",u.getEmail(),nombreCompleto,"ALUMNO");
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

    @PostMapping("/cambiar_credenciales")
    public ResponseEntity<?> cambiarCredenciales(@RequestBody CambiarCredenciales c){
        Usuario u = usuarioService.getPorEmail(c.getEmailAntiguo());
        EmailValidator em = EmailValidator.getInstance();
        if(u == null){
            return new ResponseEntity<>(new Mensaje("Error! El E-Mail ingresado no pertenece a una cuenta registrada.",400),
                    HttpStatus.BAD_REQUEST);
        }
        CambiarCredenciales carga = c.limpiarDatos();
        boolean cargaValida = carga.validarDatos();
        boolean validarEmail = em.isValid(carga.getEmailNuevo());
        if(!cargaValida || !validarEmail){
            return new ResponseEntity<>(new Mensaje("Error! Los datos ingresados no cumplen con el formato correcto.",400),
                    HttpStatus.BAD_REQUEST);
        }
        boolean contrasenaCorrecta = bCryptPasswordEncoder.matches(carga.getContrasenaAntigua(),u.getContrasena());
        if(!contrasenaCorrecta){
            return new ResponseEntity<>(new Mensaje("Error! La contraseña antigua ingresada no coincide en los registros.",400),
                    HttpStatus.BAD_REQUEST);
        }
        try{
            u.setEmail(carga.getEmailNuevo());
            u.setContrasena(bCryptPasswordEncoder.encode(carga.getNuevaContrasena()));
            usuarioService.actualizar(u);
            return new ResponseEntity<>(new Mensaje("Credenciales actualizadas con exito para el usuario de ID: "+u.getCodUsuario(),200),HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Error! Imposible actualizar datos de la cuenta.",500),
                    HttpStatus.INTERNAL_SERVER_ERROR); //TODO: Tal vez algo falle aqui!
        }
    }

    //TODO: Testear.....
    @PostMapping("/registro_alumno")
    public ResponseEntity<?> registroAlumno(@RequestBody RegistroAlumno ra){
        EmailValidator em = EmailValidator.getInstance();
        Alumno a = alumnoService.getPorDNI(ra.getDni());
        Profesor p = profesorService.getPorDNI(ra.getDni());
        Usuario u = usuarioService.getPorEmail(ra.getEmail());
        Curso c = cursoService.getPorCod(ra.getCodCurso());
        List<Turno> listaTurnos = turnoService.listarTurnoPorCurso(c.getCodCurso());
        boolean cursoLleno = cursoService.cursoLleno(c.getCodCurso()); //Chequear esto...!
        if(a != null || p != null){
            return new ResponseEntity<>(new Mensaje("Error! El DNI ingresado ya pertenece a una cuenta registrada.",400),
                    HttpStatus.BAD_REQUEST);
        }
        if(u != null){
            return new ResponseEntity<>(new Mensaje("Error! El E-Mail ingresado ya pertenece a una cuenta registrada.",400),
                    HttpStatus.BAD_REQUEST);
        }
        if(c == null){
            return new ResponseEntity<>(new Mensaje("Error! El curso seleccionado no existe!",400),
                    HttpStatus.BAD_REQUEST);
        }
        if(listaTurnos.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! El curso seleccionado no tiene turno asignado!",400),
                    HttpStatus.BAD_REQUEST);
        }

        RegistroAlumno limpiarDatos = ra.limpiarDatos();
        limpiarDatos.setContrasena(limpiarDatos.getDni()+limpiarDatos.getApellidoPaterno().substring(0,1));
        limpiarDatos.setNombreUsuario(limpiarDatos.generarNombreUsuario());
        boolean datosValidos = limpiarDatos.validarDatos();
        limpiarDatos.setContrasena(bCryptPasswordEncoder.encode(limpiarDatos.getDni()+limpiarDatos.getApellidoPaterno().substring(0,1)));
        boolean emailValido = em.isValid(limpiarDatos.getEmail());
        if(!datosValidos || !emailValido){
            return new ResponseEntity<>(new Mensaje("Error! Los datos ingresados no cuentan con el formato requerido.",400),
                    HttpStatus.BAD_REQUEST);
        }
        if(cursoLleno){
            return new ResponseEntity<>(new Mensaje("Error! El curso seleccionado no cuenta con vacantes disponibles.",400),
                    HttpStatus.BAD_REQUEST);
        }
        //Insert de datos!!.
        try{
            //Usuario -> Alumno -> Inscripción
            Rol r = new Rol(3L,"ALUMNO");
            Usuario usuario = new Usuario(0L,limpiarDatos.getNombreUsuario(),limpiarDatos.getContrasena(),
                    limpiarDatos.getEmail(),true,r);
            Usuario usuarioGuardado = usuarioService.guardar(usuario);
            Alumno alumno = new Alumno(0L,limpiarDatos.getNombre(),limpiarDatos.getApellidoPaterno(),
                    limpiarDatos.getApellidoMaterno(),limpiarDatos.getDni(),
                    limpiarDatos.getTelefono(),true,usuarioGuardado);
            Alumno alumnoGuardado = alumnoService.guardar(alumno);
            Inscripcion inscripcion = new Inscripcion(0L,ra.getFechaInicio(),
                    ra.getFechaFinal(),ra.getFechaInscripcion(),
                    null,true,new Alumno(alumnoGuardado.getCodAlumno()),
                    c,listaTurnos.get(0));
            Inscripcion inscripcionGuardada = inscripcionService.guardar(inscripcion);
            emailService.enviarEmailInscripcion(limpiarDatos,c.getNombre(),c.getFrecuencia(),listaTurnos.get(0));
            return new ResponseEntity<>(ra,HttpStatus.CREATED);
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Error! Ha sucedido en error en el guardado de datos.",500),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
