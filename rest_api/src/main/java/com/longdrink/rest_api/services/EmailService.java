package com.longdrink.rest_api.services;

import com.longdrink.rest_api.model.Turno;
import com.longdrink.rest_api.model.payload.RegistroAlumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Async //Algo falla? este es tu hombre.
    public void enviarEmailInscripcion(RegistroAlumno alumno, String nombreCurso, String frecuencia, Turno t){
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(alumno.getEmail());
        mensaje.setSubject("Long Drink Bar Training - Información de tu Registro");
        mensaje.setText("¡Bienvenido a Long Drink Bar Training! \n"+
                "Has recibido este e-mail debido a que has sido matriculado en uno de nuestros cursos. \n"+
                "A continuación adjuntamos los datos de tu cuenta para que puedas iniciar sesión facilmente en nuestro sistema. \n\n"+
                "NOMBRE DE USUARIO: "+alumno.getNombreUsuario()+"\n"+
                "CONTRASEÑA: "+alumno.getDni()+alumno.getApellidoPaterno().substring(0,1)+"\n"+
                "NOMBRE COMPLETO: "+ alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()+"\n"+
                "E-MAIL: "+alumno.getEmail()+"\n\n"+
                "Te recordamos que te has inscrito en el siguiente curso: \n"+
                "CURSO MATRICULADO: "+nombreCurso+"\n"+
                "PRIMER DÍA DE CLASES: "+new SimpleDateFormat("dd-MM-yyyy").format(alumno.getFechaInicio())+"\n"+
                "ÚLTIMO DÍA DE CLASES: "+new SimpleDateFormat("dd-MM-yyyy").format(alumno.getFechaFinal())+"\n"+
                "FRECUENCIA: "+frecuencia+"\n"+
                "HORA INICIO: "+new SimpleDateFormat("HH:mm").format(t.getHoraInicio())+" HORA FIN: "+new SimpleDateFormat("HH:mm").format(t.getHoraFin())+"\n\n"+
                "Te recordamos que debes cambiar tu contraseña a la brevedad posible por razones de seguridad. Puedes hacerlo iniciando sesión en la web institucional o en tu aplicativo móvil.\n"+
                "Recuerda no compartir tus credenciales con nadie.\n ¿Dudas? ¡Consultas? ¿Sugerencias? Comunicarse con administración: 994245306\n"+
                "Este es un e-mail generado automaticamente y no recibe respuestas de ningun tipo.");
        mensaje.setFrom("sistemas.it.longdrink@gmail.com");
        mailSender.send(mensaje);
    }
}
