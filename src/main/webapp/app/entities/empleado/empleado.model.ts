import { IVenta } from 'app/entities/venta/venta.model';

export interface IEmpleado {
  id?: number;
  nombre?: string;
  apellidos?: string;
  dni?: string;
  ventas?: IVenta[] | null;
}

export class Empleado implements IEmpleado {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellidos?: string,
    public dni?: string,
    public ventas?: IVenta[] | null
  ) {}
}

export function getEmpleadoIdentifier(empleado: IEmpleado): number | undefined {
  return empleado.id;
}
