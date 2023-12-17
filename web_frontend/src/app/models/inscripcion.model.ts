import { Alumno } from "./alumno.model";
import { Asistencia } from "./asistencia.model";
import { Deserializable } from "./deserializable.model";
import { Seccion } from "./seccion.model";
import { Turno } from "./turno.model";

export class Inscripcion implements Deserializable {
    codInscripcion?: number;
    estado?: boolean;
    fechaInscripcion?: Date;
    fechaTerminado?: Date;
    alumno?: Alumno;
    curso?: number | any;
    turno: Turno = new Turno;
    seccion?: Seccion;
    asistencias?: Asistencia[];
    cantidadFaltas?: number; //Auxiliar asistencias.

    deserializable(input: any): this {
        Object.assign(this, input);
        return this;
    }

    definirEstado(): string{
        let estado = this.estado as boolean;
        let fechaT = this.fechaTerminado;
        let fechaI = this.fechaInscripcion;
        if(estado as boolean == false && fechaT == null){
          return 'PENDIENTE';
        }
        if(estado as boolean == true && fechaT == null || fechaT != null){
          return 'ACEPTADA';
        }
        if(estado as boolean == false && fechaT == fechaI){
          return 'RECHAZADA';
        }
        return 'INDEFINIDO'
      }
}
