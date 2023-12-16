import { Deserializable } from "./deserializable.model";
import { Inscripcion } from "./inscripcion.model";
import { Pago } from "./pago.model";

export class Alumno implements Deserializable{
    nombre?: string;
    dni?: string;
    telefono?: string;
    activo?: boolean;
    inscripciones?: Inscripcion[];
    codAlumno?: number;
    apellidoPaterno?: string;
    apellidoMaterno?: string;
    pagos?: Pago[] = [];
    deserializable(input: any): this {
        Object.assign(this, input);
        return this;
    }
}
