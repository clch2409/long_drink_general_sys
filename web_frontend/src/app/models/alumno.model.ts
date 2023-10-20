import { Deserializable } from "./deserializable.model";
import { Inscripcion } from "./inscripcion.model";

export class Alumno implements Deserializable{
    nombre?: string;
    dni?: string;
    telefono?: string;
    activo?: boolean;
    inscripciones?: Inscripcion[];
    codAlumno?: number;
    apellidoPaterno?: string;
    apellidoMaterno?: string;
    deserializable(input: any): this {
        Object.assign(this, input);
        return this;
    }
}