package com.longdrink.rest_api.utils;

import java.awt.Color;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.longdrink.rest_api.model.Alumno;
import com.longdrink.rest_api.model.Curso;
import com.longdrink.rest_api.model.Inscripcion;
import com.longdrink.rest_api.model.Profesor;
import com.longdrink.rest_api.services.AlumnoService;
import com.longdrink.rest_api.services.CursoService;
import com.longdrink.rest_api.services.InscripcionService;
import com.longdrink.rest_api.services.ProfesorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExportarPdf{
    @Autowired
    AlumnoService alumnoService = new AlumnoService();
    @Autowired
    CursoService cursoService = new CursoService();
    @Autowired
    ProfesorService profesorService = new ProfesorService();
    @Autowired
    InscripcionService inscripcionService = new InscripcionService();

    List<Alumno> listadoAlumnos;
    List<Curso> listadoCursos;
    List<Profesor> listadoProfesores;
    List<Inscripcion> listadoInscripciones;

    public void exportAlumnos(HttpServletResponse response, int activos) throws IOException{
        
        
        

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        //Se modifica el tamaño y el color de la fuente
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.white);
        Font fuenteTituloColumnas = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.white);
        // Font fuenteCeldas = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.black);

        //Se redimensiona el documento, tanto su tamaño como los márgenes
        document.setPageSize(PageSize.LETTER.rotate());
        document.setMargins(-20, -20, 40, 20);
        //Se abre el documento para que tome los cambios en las dimensiones
        document.open();

        //Se agrega una tabla solo para el título
        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell celda = null;

        //Se hacen modificaciones de estilo al titulo, en ese caso de la celda que se encontrará en el título
        if (activos == 1){
            celda = new PdfPCell(new Phrase("Listado General de Alumnos", fuenteTitulo));
            listadoAlumnos = alumnoService.listarAlumnosActivos();
        }
        else{
            celda = new PdfPCell(new Phrase("Listado de Alumnos Activos", fuenteTitulo));
            listadoAlumnos = alumnoService.listarAlumnos();
        }
        
        celda.setBorder(0);
        celda.setBackgroundColor(new Color(40, 190, 138));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(30);

        tablaTitulo.addCell(celda);
        tablaTitulo.setSpacingAfter(30);


        //Se crea la tabla de titulo
        PdfPTable tablaAlumnosGeneral = new PdfPTable(6);
        tablaAlumnosGeneral.setWidths(new float[] {1f, 2f, 2f, 1.5f, 1.5f, 2f});

        celda = new PdfPCell(new Phrase("Código", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaAlumnosGeneral.addCell(celda);

        celda = new PdfPCell(new Phrase("Nombres", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaAlumnosGeneral.addCell(celda);

        celda = new PdfPCell(new Phrase("Apellidos", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaAlumnosGeneral.addCell(celda);

        celda = new PdfPCell(new Phrase("DNI", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaAlumnosGeneral.addCell(celda);

        celda = new PdfPCell(new Phrase("Teléfono", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaAlumnosGeneral.addCell(celda);

        celda = new PdfPCell(new Phrase("Email", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaAlumnosGeneral.addCell(celda);



        //Se agrega a la tabla los datos de los cliente y se agrega una celda con cada dato del cliente
        listadoAlumnos.forEach(alumno -> {
            tablaAlumnosGeneral.addCell(alumno.getCodAlumno().toString());
            tablaAlumnosGeneral.addCell(alumno.getNombre());
            tablaAlumnosGeneral.addCell(alumno.getApellidoPaterno() + " " + alumno.getApellidoMaterno());
            tablaAlumnosGeneral.addCell(alumno.getDni());
            tablaAlumnosGeneral.addCell(alumno.getTelefono());
            tablaAlumnosGeneral.addCell(alumno.getUsuario().getEmail());
        });

        //Se agregan las dos tablas al documento
        document.add(tablaTitulo);
        document.add(tablaAlumnosGeneral);
        document.close();
    }

    public void exportCursos(HttpServletResponse response, int visibles) throws IOException{
        

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        //Se modifica el tamaño y el color de la fuente
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.white);
        Font fuenteTituloColumnas = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.white);
        // Font fuenteCeldas = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.black);

        //Se redimensiona el documento, tanto su tamaño como los márgenes
        document.setPageSize(PageSize.LETTER.rotate());
        document.setMargins(-20, -20, 40, 20);
        //Se abre el documento para que tome los cambios en las dimensiones
        document.open();

        //Se agrega una tabla solo para el título
        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell celda = null;

        //Se hacen modificaciones de estilo al titulo, en ese caso de la celda que se encontrará en el título
        if (visibles == 1){
            celda = new PdfPCell(new Phrase("Listado General de Cursos", fuenteTitulo));
            listadoCursos = cursoService.listarCursos();
        }
        else{
            celda = new PdfPCell(new Phrase("Listado de Cursos Visibles", fuenteTitulo));
            listadoCursos = cursoService.listarCursosVisibles();
        }
        celda.setBorder(0);
        celda.setBackgroundColor(new Color(5, 10, 230));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(30);

        tablaTitulo.addCell(celda);
        tablaTitulo.setSpacingAfter(30);


        //Se crea la tabla de titulo
        PdfPTable tablaCursos = new PdfPTable(8);
        tablaCursos.setWidths(new float[] {1f, 1.5f, 3.5f, 1.5f, 1.5f, 2f, 2f, 2f});

        celda = new PdfPCell(new Phrase("Código", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaCursos.addCell(celda);

        celda = new PdfPCell(new Phrase("Nombre", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaCursos.addCell(celda);

        celda = new PdfPCell(new Phrase("Descripcion", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaCursos.addCell(celda);

        celda = new PdfPCell(new Phrase("Mensualidad", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaCursos.addCell(celda);

        celda = new PdfPCell(new Phrase("Duración", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaCursos.addCell(celda);

        celda = new PdfPCell(new Phrase("# Alumnos", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaCursos.addCell(celda);

        celda = new PdfPCell(new Phrase("Frecuencia", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaCursos.addCell(celda);

        celda = new PdfPCell(new Phrase("Docente", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaCursos.addCell(celda);



        //Se agrega a la tabla los datos de los cliente y se agrega una celda con cada dato del cliente
        listadoCursos.forEach(curso -> {
            int cantidad = (int)curso.getCantidadAlumnos();
            tablaCursos.addCell(curso.getCodCurso().toString());
            tablaCursos.addCell(curso.getNombre());
            tablaCursos.addCell(curso.getDescripcion());
            tablaCursos.addCell("S/." + curso.getMensualidad());
            tablaCursos.addCell(curso.getDuracion() + " semanas");
            tablaCursos.addCell(cantidad+"");
            tablaCursos.addCell(curso.getFrecuencia());
            tablaCursos.addCell(curso.getProfesor().getNombre() + " " + curso.getProfesor().getApellidoPaterno());
        });

        //Se agregan las dos tablas al documento
        document.add(tablaTitulo);
        document.add(tablaCursos);
        document.close();
    }


    public void exportDocentes(HttpServletResponse response, int activo) throws IOException{

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        //Se modifica el tamaño y el color de la fuente
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.white);
        Font fuenteTituloColumnas = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.white);
        // Font fuenteCeldas = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.black);

        //Se redimensiona el documento, tanto su tamaño como los márgenes
        document.setPageSize(PageSize.LETTER.rotate());
        document.setMargins(-20, -20, 40, 20);
        //Se abre el documento para que tome los cambios en las dimensiones
        document.open();

        //Se agrega una tabla solo para el título
        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell celda = null;

        //Se hacen modificaciones de estilo al titulo, en ese caso de la celda que se encontrará en el título
        if (activo == 1){
            celda = new PdfPCell(new Phrase("Listado General de Docentes", fuenteTitulo));
            listadoProfesores = profesorService.listarProfesores();
        }
        else{
            celda = new PdfPCell(new Phrase("Listado de Docentes Activos", fuenteTitulo));
            listadoProfesores = profesorService.listarActivos();
        }
        celda.setBorder(0);
        celda.setBackgroundColor(new Color(253, 36, 76));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(30);

        tablaTitulo.addCell(celda);
        tablaTitulo.setSpacingAfter(30);


        //Se crea la tabla de titulo
        PdfPTable tablaDocentes = new PdfPTable(7);
        tablaDocentes.setWidths(new float[] {1f, 1.5f, 3.5f, 1.5f, 1.5f, 2f, 2f});

        celda = new PdfPCell(new Phrase("Código", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaDocentes.addCell(celda);

        celda = new PdfPCell(new Phrase("Nombres", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaDocentes.addCell(celda);

        celda = new PdfPCell(new Phrase("Apellidos", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaDocentes.addCell(celda);

        celda = new PdfPCell(new Phrase("DNI", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaDocentes.addCell(celda);

        celda = new PdfPCell(new Phrase("Email", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaDocentes.addCell(celda);

        celda = new PdfPCell(new Phrase("Telefono", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaDocentes.addCell(celda);

        celda = new PdfPCell(new Phrase("Fecha de Contratación", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaDocentes.addCell(celda);

        //Se agrega a la tabla los datos de los cliente y se agrega una celda con cada dato del cliente
        listadoProfesores.forEach(profesor -> {
            tablaDocentes.addCell(profesor.getCodProfesor().toString());
            tablaDocentes.addCell(profesor.getNombre());
            tablaDocentes.addCell(profesor.getApellidoPaterno() + " " + profesor.getApellidoMaterno());
            tablaDocentes.addCell(profesor.getDni());
            tablaDocentes.addCell(profesor.getTelefono());
            tablaDocentes.addCell(profesor.getUsuario().getEmail());
            tablaDocentes.addCell(profesor.getFechaContratacion().toString());
        });

        //Se agregan las dos tablas al documento
        document.add(tablaTitulo);
        document.add(tablaDocentes);
        document.close();
    }

    public void exportInscripcion(HttpServletResponse response, int tipo, String codigo) throws IOException{

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        //Se modifica el tamaño y el color de la fuente
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.white);
        Font fuenteTituloColumnas = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.white);
        // Font fuenteCeldas = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.black);

        //Se redimensiona el documento, tanto su tamaño como los márgenes
        document.setPageSize(PageSize.LETTER.rotate());
        document.setMargins(-20, -20, 40, 20);
        //Se abre el documento para que tome los cambios en las dimensiones
        document.open();

        //Se agrega una tabla solo para el título
        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell celda = null;

        //Se hacen modificaciones de estilo al titulo, en ese caso de la celda que se encontrará en el título
        celda = new PdfPCell(new Phrase("Listado de Inscripciones", fuenteTitulo));
        if (tipo == 1){
            listadoInscripciones = inscripcionService.listarPorDniAlumno(codigo);
        }
        else if (tipo == 2){
            listadoInscripciones = inscripcionService.listarPorCurso(Long.valueOf(codigo));
        }
        else{
            listadoInscripciones = inscripcionService.listarInscripciones();
        }
        celda.setBorder(0);
        celda.setBackgroundColor(new Color(253, 36, 76));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(30);

        tablaTitulo.addCell(celda);
        tablaTitulo.setSpacingAfter(30);


        //Se crea la tabla de titulo
        PdfPTable tablaInscripciones = new PdfPTable(8);
        tablaInscripciones.setWidths(new float[] {1f, 1.5f, 3.5f, 1.5f, 1.5f, 2f, 2f, 2f});

        celda = new PdfPCell(new Phrase("Código", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaInscripciones.addCell(celda);

        celda = new PdfPCell(new Phrase("Alumno", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaInscripciones.addCell(celda);

        celda = new PdfPCell(new Phrase("Curso", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaInscripciones.addCell(celda);

        celda = new PdfPCell(new Phrase("Fecha Inicio", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaInscripciones.addCell(celda);

        celda = new PdfPCell(new Phrase("Fecha Fin", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaInscripciones.addCell(celda);

        celda = new PdfPCell(new Phrase("Fecha Inscripcion", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaInscripciones.addCell(celda);

        celda = new PdfPCell(new Phrase("Fecha Terminado", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaInscripciones.addCell(celda);

        celda = new PdfPCell(new Phrase("Turno", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaInscripciones.addCell(celda);

        //Se agrega a la tabla los datos de los cliente y se agrega una celda con cada dato del cliente
        listadoInscripciones.forEach(inscripcion -> {
            //ODIO TRABAJAR CON FECHAS -- TE ODIO TIPO DE DATO DATE!!!!!!!!!
            SimpleDateFormat deStringAFecha = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat deFechaAString = new SimpleDateFormat("dd-MM-yyyy");


            Date fechaInicioFormateada;
            Date fechaFinalFormateada;
            Date fechaTerminadoFormateada = null;
            Date fechaInscripcionFormateada;
            String fechaInicioString;
            String fechaFinalString;
            String fechaTerminadoString;
            String fechaInscripcionString;
            try {
                fechaInicioFormateada = deStringAFecha.parse(inscripcion.getFechaInicio().toString());
                fechaFinalFormateada = deStringAFecha.parse(inscripcion.getFechaFinal().toString());
                if (inscripcion.getFechaTerminado() != null){
                    fechaTerminadoFormateada = deStringAFecha.parse(inscripcion.getFechaTerminado().toString());
                }
                fechaInscripcionFormateada = deStringAFecha.parse(inscripcion.getFechaInscripcion().toString());
                
                fechaInicioString = deFechaAString.format(fechaInicioFormateada);
                fechaFinalString = deFechaAString.format(fechaFinalFormateada);
                fechaTerminadoString = fechaTerminadoFormateada == null ? "En Curso" : deFechaAString.format(fechaTerminadoFormateada);
                fechaInscripcionString = deFechaAString.format(fechaInscripcionFormateada);


                tablaInscripciones.addCell(inscripcion.getCodInscripcion().toString());
                tablaInscripciones.addCell(inscripcion.getAlumno().getNombre() + " " + inscripcion.getAlumno().getApellidoPaterno());
                tablaInscripciones.addCell(inscripcion.getCurso().getNombre());
                tablaInscripciones.addCell(fechaInicioString);
                tablaInscripciones.addCell(fechaFinalString);
                tablaInscripciones.addCell(fechaTerminadoString);
                tablaInscripciones.addCell(fechaInscripcionString);
                tablaInscripciones.addCell(inscripcion.getTurno().getNombre());

            } catch (ParseException e) {
                e.printStackTrace();
            }
            
        });

        //Se agregan las dos tablas al documento
        document.add(tablaTitulo);
        document.add(tablaInscripciones);
        document.close();
    }
    
}
