import { Deserializable } from "./deserializable.model";

export class Profesor implements Deserializable {
    codProfesor?: number;
    nombre?: string;
    apellidoPaterno?: string;
    apellidoMaterno?: string;
    dni?: string;
    telefono?: string;
    fechaContratacion?: Date;
    activo?: boolean;
    email?: string;
    contrasena?: string;

    deserializable(input: any): this {
        Object.assign(this, input);
        return this;
    }
}
