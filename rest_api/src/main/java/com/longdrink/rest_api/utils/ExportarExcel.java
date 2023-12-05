package com.longdrink.rest_api.utils;

import com.longdrink.rest_api.model.Alumno;
import com.longdrink.rest_api.model.Curso;
import com.longdrink.rest_api.model.Inscripcion;
import com.longdrink.rest_api.model.Profesor;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ExportarExcel {
    private XSSFWorkbook libro;
    private XSSFSheet hoja;
    private List<Curso> listaCursos;
    private List<Profesor> listaProfesores;
    private List<Alumno> listaAlumnos;
    private List<Inscripcion> listaInscripciones;

    public ExportarExcel(List<Curso> listaCursos){
        this.listaCursos = listaCursos;
        this.libro = new XSSFWorkbook();
    }

    public ExportarExcel(List<Profesor> listaProfesores,int a){
        this.listaProfesores = listaProfesores;
        this.libro = new XSSFWorkbook();
    }

    public ExportarExcel(List<Alumno> listaAlumnos,String a){
        this.listaAlumnos = listaAlumnos;
        this.libro = new XSSFWorkbook();
    }

    public ExportarExcel(List<Inscripcion> listaInscripciones,Long a){
        this.listaInscripciones = listaInscripciones;
        this.libro = new XSSFWorkbook();
    }

    private void crearCelda(Row fila, int conteoColumna, Object valor, CellStyle estiloCelda){
        hoja.autoSizeColumn(conteoColumna);
        Cell celda = fila.createCell(conteoColumna);
        if(valor instanceof Integer){ celda.setCellValue((Integer)valor); }
        else if(valor instanceof Long){celda.setCellValue((Long)valor);}
        else if(valor instanceof Boolean){ celda.setCellValue((Boolean)valor); }
        else if(valor instanceof Double){ celda.setCellValue((Double)valor); }
        else if(valor instanceof Date){ celda.setCellValue((Date)valor);}
        else if(valor instanceof Byte){ celda.setCellValue((Byte)valor);}
        else{ celda.setCellValue((String)valor); }
        celda.setCellStyle(estiloCelda);
    }
    //Inicio -> Exportar docentes a Excel.
    private void escribirHeaderProfesores(){
        hoja = libro.createSheet("PROFESORES");
        Row fila = hoja.createRow(0);
        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        estilo.setFont(fuente);
        crearCelda(fila,0,"COD. PROFESOR",estilo);
        crearCelda(fila,1,"AP. PATERNO",estilo);
        crearCelda(fila,2,"AP. MATERNO",estilo);
        crearCelda(fila,3,"NOMBRE",estilo);
        crearCelda(fila,4,"DNI",estilo);
        crearCelda(fila,5,"TELÉFONO",estilo);
        crearCelda(fila,6,"FECHA CONTRATACIÓN",estilo);
        crearCelda(fila,7,"ESTADO",estilo);
    }

    private void escribirProfesores(){
        int filaActual = 1;
        CellStyle estilo = libro.createCellStyle();
        XSSFCreationHelper helper = libro.getCreationHelper();
        XSSFCellStyle estiloFecha = libro.createCellStyle();
        estiloFecha.setDataFormat(helper.createDataFormat().getFormat("dd/MM/yyyy"));
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);
        estiloFecha.setFont(fuente);
        for(Profesor p : listaProfesores){
            Row fila = hoja.createRow(filaActual++);
            int columnaActual = 0;
            crearCelda(fila,columnaActual++,p.getCodProfesor(),estilo);
            crearCelda(fila,columnaActual++,p.getApellidoPaterno(),estilo);
            crearCelda(fila,columnaActual++,p.getApellidoMaterno(),estilo);
            crearCelda(fila,columnaActual++,p.getNombre(),estilo);
            crearCelda(fila,columnaActual++,p.getDni(),estilo);
            crearCelda(fila,columnaActual++,p.getTelefono(),estilo);
            crearCelda(fila,columnaActual++,p.getFechaContratacion(),estiloFecha);
            crearCelda(fila,columnaActual++,p.isActivo(),estilo);
        }
    }

    public void exportarProfesores(HttpServletResponse response) throws IOException {
        escribirHeaderProfesores();
        escribirProfesores();
        ServletOutputStream outputStream = response.getOutputStream();
        libro.write(outputStream);
        libro.close();
        outputStream.close();
    }
    //Fin -> Exportar docentes a Excel.

    //Inicio -> Exportar cursos a Excel.
    private void escribirHeaderCursos(){
        hoja = libro.createSheet("CURSOS");
        Row fila = hoja.createRow(0);
        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        estilo.setFont(fuente);
        crearCelda(fila,0,"COD. CURSO",estilo);
        crearCelda(fila,1,"NOMBRE",estilo);
        crearCelda(fila,2,"DESCRIPCIÓN",estilo);
        crearCelda(fila,3,"FRECUENCIA",estilo);
        crearCelda(fila,4,"DURACIÓN",estilo);
        crearCelda(fila,5,"MENSUALIDAD",estilo);
        crearCelda(fila,6,"CANTIDAD ALUMNOS",estilo);
        crearCelda(fila,7,"PROFESOR",estilo);
        crearCelda(fila,8,"VISIBLE",estilo);
    }

    private void escribirCursos(){
        int filaActual = 1;
        CellStyle estilo = libro.createCellStyle();
        XSSFCreationHelper helper = libro.getCreationHelper();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);
        for(Curso c : listaCursos){
            Row fila = hoja.createRow(filaActual++);
            int columnaActual = 0;
            crearCelda(fila,columnaActual++,c.getCodCurso(),estilo);
            crearCelda(fila,columnaActual++,c.getNombre(),estilo);
            crearCelda(fila,columnaActual++,c.getDescripcion(),estilo);
            crearCelda(fila,columnaActual++,c.getFrecuencia(),estilo);
            crearCelda(fila,columnaActual++,c.getDuracion() + " SEMANAS",estilo);
            crearCelda(fila,columnaActual++,c.getMensualidad(),estilo);
            //crearCelda(fila,columnaActual++,c.getCantidadAlumnos(),estilo);
            crearCelda(fila,columnaActual++,c.getProfesor().getNombre() + " "+c.getProfesor().getApellidoPaterno(),estilo);
            crearCelda(fila,columnaActual++,c.isVisibilidad(),estilo);
        }
    }

    public void exportarCursos(HttpServletResponse response) throws IOException {
        escribirHeaderCursos();
        escribirCursos();
        ServletOutputStream outputStream = response.getOutputStream();
        libro.write(outputStream);
        libro.close();
        outputStream.close();
    }
    //Fin -> Exportar cursos a Excel.

    //Inicio -> Exportar alumnos a Excel.
    private void escribirHeaderAlumnos(){
        hoja = libro.createSheet("ALUMNOS");
        Row fila = hoja.createRow(0);
        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        estilo.setFont(fuente);
        crearCelda(fila,0,"COD. ALUMNO",estilo);
        crearCelda(fila,1,"AP. PATERNO",estilo);
        crearCelda(fila,2,"AP. MATERNO",estilo);
        crearCelda(fila,3,"NOMBRE",estilo);
        crearCelda(fila,4,"DNI",estilo);
        crearCelda(fila,5,"TELÉFONO",estilo);
        crearCelda(fila,6,"USUARIO",estilo);
        crearCelda(fila,7,"ÚLTIMO CURSO",estilo);
        crearCelda(fila,8,"ESTADO",estilo);
    }

    private void escribirAlumnos(){
        int filaActual = 1;
        CellStyle estilo = libro.createCellStyle();
        XSSFCreationHelper helper = libro.getCreationHelper();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);
        for(Alumno a: listaAlumnos){
            Row fila = hoja.createRow(filaActual++);
            int columnaActual = 0;
            crearCelda(fila,columnaActual++,a.getCodAlumno(),estilo);
            crearCelda(fila,columnaActual++,a.getApellidoPaterno(),estilo);
            crearCelda(fila,columnaActual++,a.getApellidoMaterno(),estilo);
            crearCelda(fila,columnaActual++,a.getNombre(),estilo);
            crearCelda(fila,columnaActual++,a.getDni(),estilo);
            crearCelda(fila,columnaActual++,a.getTelefono(),estilo);
            crearCelda(fila,columnaActual++,a.getUsuario().getNombreUsuario(),estilo);
            if(!a.getInscripciones().isEmpty()){
                //crearCelda(fila,columnaActual++,a.getInscripciones().get(a.getInscripciones().size() - 1).getCurso().getNombre(),estilo);
            }
            else{
                crearCelda(fila,columnaActual++,"N/A",estilo);
            }
            crearCelda(fila,columnaActual++, a.isActivo() ? "ACTIVO" : "INACTIVO",estilo);
        }
    }

    public void exportarAlumnos(HttpServletResponse response) throws IOException {
        escribirHeaderAlumnos();
        escribirAlumnos();
        ServletOutputStream outputStream = response.getOutputStream();
        libro.write(outputStream);
        libro.close();
        outputStream.close();
    }
    //Fin -> Exportar alumnos a Excel.

    //Inicio -> Exportar inscripciones a Excel.
    private void escribirHeaderInscripciones(){
        hoja = libro.createSheet("INSCRIPCIONES");
        Row fila = hoja.createRow(0);
        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        estilo.setFont(fuente);
        crearCelda(fila,0,"COD. INSCRIPCIÓN",estilo);
        crearCelda(fila,1,"CURSO",estilo);
        crearCelda(fila,2,"FRECUENCIA",estilo);
        crearCelda(fila,3,"TURNO",estilo);
        crearCelda(fila,4,"FECHA DE INSCRIPCIÓN",estilo);
        crearCelda(fila,5,"FECHA INICIO",estilo);
        crearCelda(fila,6,"FECHA FIN - PROGRAMADA",estilo);
        crearCelda(fila,7,"FECHA FIN",estilo);
        crearCelda(fila,8,"ESTADO",estilo);
    }
    //TODO: SPT3 - REDISEÑAR.
    private void escribirInscripcion(){
        int filaActual = 1;
        CellStyle estilo = libro.createCellStyle();
        XSSFCreationHelper helper = libro.getCreationHelper();
        XSSFFont fuente = libro.createFont();
        XSSFCellStyle estiloFecha = libro.createCellStyle();
        estiloFecha.setDataFormat(helper.createDataFormat().getFormat("dd/MM/yyyy"));
        fuente.setFontHeight(14);
        estilo.setFont(fuente);
        estiloFecha.setFont(fuente);
        for(Inscripcion i: listaInscripciones){
            Row fila = hoja.createRow(filaActual++);
            int columnaActual = 0;
            crearCelda(fila,columnaActual++,i.getCodInscripcion(),estilo);
//            crearCelda(fila,columnaActual++,i.getCurso().getNombre(),estilo);
//            crearCelda(fila,columnaActual++,i.getCurso().getFrecuencia(),estilo);
//            crearCelda(fila,columnaActual++,i.getCurso().getTurnos().get(0).getNombre(),estilo);
//            crearCelda(fila,columnaActual++,i.getFechaInscripcion(),estiloFecha);
//            crearCelda(fila,columnaActual++,i.getFechaInicio(),estiloFecha);
//            crearCelda(fila,columnaActual++,i.getFechaFinal() == null ? "N/A" : i.getFechaFinal(),i.getFechaFinal() == null ? estilo : estiloFecha);
            crearCelda(fila,columnaActual++,i.getFechaTerminado() == null ? "N/A" : i.getFechaTerminado(),i.getFechaTerminado() == null ? estilo : estiloFecha);
            crearCelda(fila,columnaActual++,i.getFechaTerminado() == null ? "EN PROCESO" : "TERMINADO",estilo);
        }
    }

    public void exportarInscripcion(HttpServletResponse response) throws IOException {
        escribirHeaderInscripciones();
        escribirInscripcion();
        ServletOutputStream outputStream = response.getOutputStream();
        libro.write(outputStream);
        libro.close();
        outputStream.close();
    }
    //Fin -> Exportar inscripciones a Excel.
}
