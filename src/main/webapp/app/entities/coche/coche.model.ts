import * as dayjs from 'dayjs';
import { IVenta } from 'app/entities/venta/venta.model';

export interface ICoche {
  id?: number;
  color?: string;
  modelo?: string;
  marca?: string;
  anio?: dayjs.Dayjs | null;
  precio?: number;
  venta?: IVenta | null;
}

export class Coche implements ICoche {
  constructor(
    public id?: number,
    public color?: string,
    public modelo?: string,
    public marca?: string,
    public anio?: dayjs.Dayjs | null,
    public precio?: number,
    public venta?: IVenta | null
  ) {}
}

export function getCocheIdentifier(coche: ICoche): number | undefined {
  return coche.id;
}
