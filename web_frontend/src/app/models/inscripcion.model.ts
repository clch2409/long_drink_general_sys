import { Deserializable } from "./deserializable.model";

export class Inscripcion implements Deserializable{
    inscripcionPk?:{
        codAlumno: number;
        codCurso: number;
    }
    estado?: boolean;
    fechaInicio?: Date; //O string?
    fechaFinal?: Date;
    fechaInscripcion?: Date;
    fechaTerminado?: Date; 
    deserializable(input: any): this {
        Object.assign(this, input);
        return this;
    }
}