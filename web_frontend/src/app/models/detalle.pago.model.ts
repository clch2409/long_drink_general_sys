import { Deserializable } from "./deserializable.model";

export class DetallePago implements Deserializable{
  codDetallePago?: number;
  concepto?: string;
  monto?: number;
  montoMora?: number;
  subTotal?: number;
  deserializable(input: any): this {
    Object.assign(this, input);
    return this;
}
public constructor(init?:Partial<DetallePago>) {
  Object.assign(this, init);
}
}
