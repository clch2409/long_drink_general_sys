import { Alumno } from "./alumno.model";
import { Curso } from "./curso.model";
import { Deserializable } from "./deserializable.model";

export class Inscripcion implements Deserializable {
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

    deserializable(input: any): this {
        Object.assign(this, input);
        return this;
    }
}  