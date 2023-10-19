import { Deserializable } from "./deserializable.model";

export class Turno implements Deserializable{
    nombre: string | undefined;
    codTurno: number | undefined;
    horaInicio: string | undefined;
    horaFin: string | undefined;
    deserializable(input: any): this {
        Object.assign(this, input);
        return this;
    }
}