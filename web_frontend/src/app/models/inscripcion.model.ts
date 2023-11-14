import { Alumno } from "./alumno.model";
import { Curso } from "./curso.model";
import { Deserializable } from "./deserializable.model";
import { Turno } from "./turno.model";

export class Inscripcion implements Deserializable {
    codInscripcion?: number;
    inscripcionPk?: {
        codAlumno: number;
        codCurso: number;
    };
    estado?: boolean;
    fechaInicio?: Date;
    fechaFinal?: Date;
    fechaInscripcion?: Date;
    fechaTerminado?: Date;
    alumno?: Alumno;
    curso?: Curso;
    turno?: Turno;

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