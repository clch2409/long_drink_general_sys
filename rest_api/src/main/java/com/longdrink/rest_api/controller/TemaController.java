package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.Tema;
import com.longdrink.rest_api.model.payload.InsertTema;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.model.payload.TemaCurso;
import com.longdrink.rest_api.services.TemaService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/tema")
public class TemaController {
    @Autowired
    private TemaService temaService;
    @Value("${media.location}")
    String ruta;

    @GetMapping
    public ResponseEntity<?> get(){
        List<Tema> listaTemas = temaService.listarTemas();
        if(listaTemas.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron temas.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaTemas,HttpStatus.OK);
    }

    @GetMapping("/temas_cursos")
    public ResponseEntity<?> getTemasCursos(){
        List<Tema> listaTemas = temaService.listarTemas();
        List<TemaCurso> retorno = new ArrayList<>();
        if(listaTemas.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron temas.",404),
                    HttpStatus.NOT_FOUND);
        }
        for(Tema t: listaTemas){
            TemaCurso temaCurso = new TemaCurso();
            BeanUtils.copyProperties(t,temaCurso);
            retorno.add(temaCurso);
        }
        return new ResponseEntity<>(retorno,HttpStatus.OK);
    }

    @GetMapping("/por_curso")
    public ResponseEntity<?> getPorCurso(@RequestParam Long codCurso){
        List<Tema> listaTemas = temaService.listarTemasPorCurso(codCurso);
        if(listaTemas.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! El curso seleccionado no tiene temas asociados.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaTemas,HttpStatus.OK);
    }

    @PostMapping("/subir_guia")
    public ResponseEntity<?> subirGuiaEstudio(@RequestParam("archivo")MultipartFile archivo, @RequestParam("nombreGuia") String nombreGuia){
        if(nombreGuia.length() >= 1 && nombreGuia.length() <= 30) {
            try {
                Path rutaGuardado = Paths.get(ruta);
                Files.copy(archivo.getInputStream(), rutaGuardado.resolve(nombreGuia + "." + FilenameUtils.getExtension(archivo.getOriginalFilename())));
                Tema temaGuardado = temaService.guardar(new Tema(0L, nombreGuia, nombreGuia + "." + FilenameUtils.getExtension(archivo.getOriginalFilename())));
                return new ResponseEntity<>(temaGuardado, HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(new Mensaje("Error! Subida de archivo fallida.", 400), HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>(new Mensaje("Error! Debe ingresar un nombre para la guía.",400),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/descargar_guia")
    public ResponseEntity<?> descargarGuiaEstudio(@RequestParam("codTema") Long codTema){
        Tema tema = temaService.obtenerTema(codTema);
        if(codTema == null){
            return new ResponseEntity<>(new Mensaje("Error! Tema no encontrado.",404),HttpStatus.NOT_FOUND);
        }
        try{
            Path rutaArchivo = Paths.get(ruta).resolve(tema.getFicha()).normalize();
            Resource recurso = new UrlResource(rutaArchivo.toUri());
            String contentType = Files.probeContentType(recurso.getFile().toPath());
            if(recurso.exists() || recurso.isReadable()){
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + recurso.getFilename() + "\"")
                        .body(recurso);
            }
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Error! Guía de estudio no encontrada o corrupta.",404),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Mensaje("Error! Guía de estudio no encontrada o corrupta.",404),HttpStatus.NOT_FOUND);
    }
}
