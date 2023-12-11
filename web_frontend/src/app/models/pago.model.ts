import { Deserializable } from "./deserializable.model";
import { DetallePago } from "./detalle.pago.model";

export class Pago implements Deserializable{
  codPago?: number;
  descripcion?: string;
  fechaPago?: Date;
  fechaVencimiento?: Date;
  total?: number;
  detallePagos?: DetallePago[];
  estado?: boolean;

  deserializable(input: any): this {
    Object.assign(this, input);
    return this;
}
public constructor(init?:Partial<Pago>) {
  Object.assign(this, init);
}
}
