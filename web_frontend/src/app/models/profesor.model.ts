import { Deserializable } from "./deserializable.model";

export class Profesor implements Deserializable{
    codProfesor?: number;
    nombre?: string;
    apellidoPaterno?: string;
    apellidoMaterno?: string;
    dni?: string;
    telefono?: string;
    fechaContratacion?: Date;
    activo?: boolean;
    deserializable(input: any): this {
        Object.assign(this, input);
        return this;
    }
}