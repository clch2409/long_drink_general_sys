import { Curso } from "./curso.model";
import { Deserializable } from "./deserializable.model";

export class TemaCurso implements Deserializable{
    codTema?: number;
    nombre?: string;
    ficha?: string;
    cursos?: Curso [];
    deserializable(input: any): this {
        Object.assign(this, input);
        return this;
    }
}