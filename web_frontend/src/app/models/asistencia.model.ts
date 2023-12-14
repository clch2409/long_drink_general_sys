import { Deserializable } from "./deserializable.model";
import { Inscripcion } from "./inscripcion.model";

export class Asistencia implements Deserializable {
  codAsistencia?: number;
  fechaAsistencia?: Date;
  horaLlegada?: Date;
  estado?: number;
  inscripcion?: Inscripcion;

  deserializable(input: any): this {
    Object.assign(this, input);
    return this;
  }

  public constructor(init?: Partial<Asistencia>) {
    Object.assign(this, init);
  }
}
