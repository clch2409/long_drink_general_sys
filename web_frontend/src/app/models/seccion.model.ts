import { Curso } from "./curso.model";
import { Deserializable } from "./deserializable.model";
import { Profesor } from "./profesor.model";
import { Tema } from "./tema.model";
import { Turno } from "./turno.model";

export class Seccion implements Deserializable{
  codSeccion?: number;
  nombre?: string;
  fechaInicio?: Date;
  fechaFinal?: Date;
  estado?: boolean;
  curso?: Curso;
  turno?: Turno;
  profesor?: Profesor;
  temas?: Tema[];
  deserializable(input: any): this {
    Object.assign(this, input);
    return this;
}
}
