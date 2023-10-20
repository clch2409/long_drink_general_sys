import { Deserializable } from "./deserializable.model";
import { Tema } from "./tema.model";
import { Turno } from "./turno.model";

export class Curso implements Deserializable {
    codCurso?: number;
    descripcion?: string;
    mensualidad?: number;
    duracion?: number;
    visibilidad?: boolean;
    frecuencia?: string;
    imagen?: string;
    profesor?: {
        nombre: string;
        dni: string;
        apellidoPaterno: string;
    };
    turnos?: Turno[];
    temas?: Tema[];
    cantidadAlumnos?: number;

    deserializable(input: any): this {
        Object.assign(this, input);
        return this;
    }
}