import { Deserializable } from "./deserializable.model";

export class Tema implements Deserializable{
    nombre: string | undefined;
    ficha: string | undefined;
    codTema: number | undefined;
    checked?: boolean;
    deserializable(input: any): this {
        Object.assign(this, input);
        return this;
    }
}