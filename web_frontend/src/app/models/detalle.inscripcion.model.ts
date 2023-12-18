import { Deserializable } from "./deserializable.model";

export class DetalleInscripcion implements Deserializable{
    codCurso?: number;
    descripcion?: string;
    mensualidad?: number;
    duracion?: number;
    cantidadAlumnos?: number;
    visibilidad?: boolean;
    imagen?: string;
    frecuencia?: string;
    //Alumno.
    codAlumno?: number;
    nombre?: string;
    apellidoPaterno?: string;
    apellidoMaterno?: string;
    dni?: string;
    telefono?: string;
    activo?: boolean;
    //Inscripcion:
    fechaInicio?: Date;
    fechaFinal?: Date;
    fechaInscripcion?: Date;
    fechaTerminado?: Date;
    estado?: boolean

    deserializable(input: any): this {
        Object.assign(this, input);
        return this;
    }
}
