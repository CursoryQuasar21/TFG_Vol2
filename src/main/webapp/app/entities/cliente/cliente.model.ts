import { IVenta } from 'app/entities/venta/venta.model';

export interface ICliente {
  id?: number;
  nombre?: string;
  apellidos?: string;
  dni?: string;
  ventas?: IVenta[] | null;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellidos?: string,
    public dni?: string,
    public ventas?: IVenta[] | null
  ) {}
}

export function getClienteIdentifier(cliente: ICliente): number | undefined {
  return cliente.id;
}
