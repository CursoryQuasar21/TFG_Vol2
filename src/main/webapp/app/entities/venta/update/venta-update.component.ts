import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map, max } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IVenta, Venta } from '../venta.model';
import { VentaService } from '../service/venta.service';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';
import { IEmpleado } from 'app/entities/empleado/empleado.model';
import { EmpleadoService } from 'app/entities/empleado/service/empleado.service';
import { ICoche } from 'app/entities/coche/coche.model';
import { CocheService } from 'app/entities/coche/service/coche.service';
import { IMoto } from 'app/entities/moto/moto.model';
import { MotoService } from 'app/entities/moto/service/moto.service';
import { elementEventFullName } from '@angular/compiler/src/view_compiler/view_compiler';

@Component({
  selector: 'jhi-venta-update',
  templateUrl: './venta-update.component.html',
})
export class VentaUpdateComponent implements OnInit {
  isSaving = false;
  //Total de la venta(Suma de los totales de los vehiculos seleccionados)
  total = 0;

  //Coleccion de clientes y empleados Para asignar a la venta
  clientesSharedCollection: ICliente[] = [];
  empleadosSharedCollection: IEmpleado[] = [];

  //Coleccion de coches y motos para poder mostrar los coches y motos disponibles
  cochesSharedCollection: ICoche[] = [];
  motosSharedCollection: IMoto[] = [];

  //Listas de los coches y motos seleccionados para la vetna
  listaCochesComprar: ICoche[] = [];
  listaMotosComprar: IMoto[] = [];

  listaCochesClonada: ICoche[] = [];
  listaMotosClonada: IMoto[] = [];

  editForm = this.fb.group({
    id: [],
    total: [null, [Validators.required, Validators.min(1)]],
    fecha: [],
    cliente: [],
    empleado: [],
  });

  constructor(
    protected ventaService: VentaService,
    protected clienteService: ClienteService,
    protected empleadoService: EmpleadoService,
    protected cocheService: CocheService,
    protected motoService: MotoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  //Metodo Modificado
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ venta }) => {
      if (venta.id === undefined) {
        const today = dayjs().startOf('day');
        venta.fecha = today;
      }
      this.listaCochesClonada = venta.coches;
      this.updateForm(venta);

      this.loadRelationshipsOptions();

      //Metodos que cargan las tablas de los vehiculos disponibles
      this.loadCoches(venta);
      this.loadMotos(venta);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const venta = this.createFromForm();
    if (venta.id !== undefined) {
      this.subscribeToSaveResponse(this.ventaService.update(venta));
    } else {
      this.subscribeToSaveResponse(this.ventaService.create(venta));
    }
  }

  trackClienteById(index: number, item: ICliente): number {
    return item.id!;
  }

  trackEmpleadoById(index: number, item: IEmpleado): number {
    return item.id!;
  }

  //Metodo Agregado
  trackCocheById(index: number, item: ICoche): number {
    return item.id!;
  }

  //Metodo Agregado
  trackMotoById(index: number, item: IMoto): number {
    return item.id!;
  }

  //Metodo Agregado
  public loadCoches(venta: Venta): void {
    //Funcion que nos devuelve todos los coches y que enfuncion de estos modifica la coleccion de coches
    this.cocheService.query().subscribe((res: HttpResponse<ICoche[]>) => {
      res.body?.forEach((coche: ICoche) => {
        //Condicion que determina si el coche esta asignado a una venta
        if (coche.venta === null) {
          //El coche no esta asigando a una venta y por tanto se puede vender
          this.cochesSharedCollection.push(coche);
        } else {
          //Condicion que determina si el coche que tiene una venta asignada es la misma venta que la que se esta mostrando
          if (venta.id === coche.venta?.id) {
            this.cochesSharedCollection.push(coche);
            //Refleja que el coche ya esta añadido a la lista de compra ya que se añadio anteriormente en una modificacion anterior de la venta
            this.anadirCocheComprado(coche);
          }
        }
      });
    });
  }

  //Metodo Agregado
  //Metodo que añade un coche a la lista de coches a comprar
  public anadirCocheComprado(coche: ICoche): void {
    this.listaCochesComprar.push(coche);
    if (coche.precio) {
      this.total = this.total + coche.precio;
      this.editForm.get(['total'])?.setValue(this.total);
    }
  }

  //Metodo Agregado
  //Metodo para añadir coches a la lista de coches a comprar
  public anadirCoche(coche: ICoche, e: any): void {
    //Camnbia el tipo de boton segun si el boton esta en añadir o eliminar
    if (e.classList.contains('btn-outline-primary')) {
      //Añade el coche a la lista de coches para comprar
      this.listaCochesComprar.push(coche);
      this.listaCochesComprar = this.ordenarListaCochesComprar(this.listaCochesComprar);
      if (coche.precio) {
        this.total = this.total + coche.precio;
        this.editForm.get(['total'])?.setValue(this.total);
      }
      //Cambia las propiedades del boton
      e.classList.remove('btn-outline-primary');
      e.classList.add('btn-outline-danger');
      e.textContent = 'Eliminar';
    } else {
      //Recorre la lista identificando, para eliminar el coche de la lista de coches a comprar
      for (let i = 0; i < this.listaCochesComprar.length; i++) {
        if (this.listaCochesComprar[i] === coche) {
          this.listaCochesComprar.splice(i, 1);
          if (coche.precio) {
            this.total = this.total - coche.precio;
            this.editForm.get(['total'])?.setValue(this.total);
          }
        }
      }
      //Cambia las propiedades del boton
      e.classList.remove('btn-outline-danger');
      e.classList.add('btn-outline-primary');
      e.textContent = 'Añadir';
    }
  }

  //Metodo agregado
  //Devuelve uan lista ordenada de la lista de coches a comparar por id
  public ordenarListaCochesComprar(lista: ICoche[]): ICoche[] {
    //Lista axuliar para los ids
    const listaId: any[] = [];
    //Guardo todos los ids en la lista auxiliar
    lista.forEach(element => listaId.push(element.id));
    //Ordeno la lista auxiliar por id
    listaId.sort(function (a, b) {
      return a - b;
    });
    //Lista definitiva ordenada
    const listaOrdenada: ICoche[] = [];
    //Recorro la lista de coches o motos pasada como parametro
    for (let i = 0; i < listaId.length; i++) {
      //Comparo cada id de la lista de vehiculos por cada id de la lista de id ordenada
      for (let z = 0; z < lista.length; z++) {
        //Si el id coincide se agrega a la lista
        if (listaId[i] === lista[z].id) {
          listaOrdenada.push(lista[z]);
        }
      }
    }
    return listaOrdenada;
  }
  //Metodo Agregado
  public loadMotos(venta: Venta): void {
    //Funcion que nos devuelve todos las motos y que enfuncion de estos modifica la coleccion de motos
    this.motoService.query().subscribe((res: HttpResponse<IMoto[]>) => {
      res.body?.forEach((moto: IMoto) => {
        //Condicion que determina si la moto esta asignada a una venta
        if (moto.venta === null) {
          //La moto no esta asigando a una venta y por tanto se puede vender
          this.motosSharedCollection.push(moto);
        } else {
          //Condicion que determina si la moto que tiene una venta asignada es la misma venta que la que se esta mostrando
          if (venta.id === moto.venta?.id) {
            this.motosSharedCollection.push(moto);
            //Refleja que la moto ya esta añadida a la lista de compra ya que se añadio anteriormente en una modificacion anterior de la venta
            this.anadirMotoComprado(moto);
          }
        }
      });
    });
  }

  //Metodo Agregado
  //Metodo que añade una moto a la lista de motos a comprar
  public anadirMotoComprado(moto: IMoto): void {
    this.listaMotosComprar.push(moto);
    if (moto.precio) {
      this.total = this.total + moto.precio;
      this.editForm.get(['total'])?.setValue(this.total);
    }
  }

  //Metodo Agregado
  //Metodo para añadir motos a la lista de motos a comprar
  public anadirMoto(moto: IMoto, e: any): void {
    //Camnbia el tipo de boton segun si el boton esta en añadir o eliminar
    if (e.classList.contains('btn-outline-primary')) {
      //Añade ma moto a la lista de motos para comprar
      this.listaMotosComprar.push(moto);
      this.listaMotosComprar = this.ordenarListaMotosComprar(this.listaMotosComprar);
      if (moto.precio) {
        this.total = this.total + moto.precio;
        this.editForm.get(['total'])?.setValue(this.total);
      }
      //Cambia las propiedades del boton
      e.classList.remove('btn-outline-primary');
      e.classList.add('btn-outline-danger');
      e.textContent = 'Eliminar';
    } else {
      //Recorre la lista identificando, para eliminar la moto de la lista de motos a comprar
      for (let i = 0; i < this.listaMotosComprar.length; i++) {
        if (this.listaMotosComprar[i] === moto) {
          this.listaMotosComprar.splice(i, 1);
        }
      }
      if (moto.precio) {
        this.total = this.total - moto.precio;
        this.editForm.get(['total'])?.setValue(this.total);
      }
      //Cambia las propiedades del boton
      e.classList.remove('btn-outline-danger');
      e.classList.add('btn-outline-primary');
      e.textContent = 'Añadir';
    }
  }
  //Metodo agregado
  //Devuelve uan lista ordenada de la lista de motos a comparar por id
  public ordenarListaMotosComprar(lista: IMoto[]): IMoto[] {
    //Lista axuliar para los ids
    const listaId: any[] = [];
    //Guardo todos los ids en la lista auxiliar
    lista.forEach(element => listaId.push(element.id));
    //Ordeno la lista auxiliar por id
    listaId.sort(function (a, b) {
      return a - b;
    });
    //Lista definitiva ordenada
    const listaOrdenada: IMoto[] = [];
    //Recorro la lista de coches o motos pasada como parametro
    for (let i = 0; i < listaId.length; i++) {
      //Comparo cada id de la lista de vehiculos por cada id de la lista de id ordenada
      for (let z = 0; z < lista.length; z++) {
        //Si el id coincide se agrega a la lista
        if (listaId[i] === lista[z].id) {
          listaOrdenada.push(lista[z]);
        }
      }
    }
    return listaOrdenada;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenta>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(venta: IVenta): void {
    this.editForm.patchValue({
      id: venta.id,
      total: venta.total,
      fecha: venta.fecha ? venta.fecha.format(DATE_TIME_FORMAT) : null,
      cliente: venta.cliente,
      empleado: venta.empleado,
    });

    this.clientesSharedCollection = this.clienteService.addClienteToCollectionIfMissing(this.clientesSharedCollection, venta.cliente);
    this.empleadosSharedCollection = this.empleadoService.addEmpleadoToCollectionIfMissing(this.empleadosSharedCollection, venta.empleado);
  }

  protected loadRelationshipsOptions(): void {
    this.clienteService
      .query()
      .pipe(map((res: HttpResponse<ICliente[]>) => res.body ?? []))
      .pipe(
        map((clientes: ICliente[]) => this.clienteService.addClienteToCollectionIfMissing(clientes, this.editForm.get('cliente')!.value))
      )
      .subscribe((clientes: ICliente[]) => (this.clientesSharedCollection = clientes));

    this.empleadoService
      .query()
      .pipe(map((res: HttpResponse<IEmpleado[]>) => res.body ?? []))
      .pipe(
        map((empleados: IEmpleado[]) =>
          this.empleadoService.addEmpleadoToCollectionIfMissing(empleados, this.editForm.get('empleado')!.value)
        )
      )
      .subscribe((empleados: IEmpleado[]) => (this.empleadosSharedCollection = empleados));
  }

  protected createFromForm(): IVenta {
    return {
      ...new Venta(),
      id: this.editForm.get(['id'])!.value,
      total: this.editForm.get(['total'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? dayjs(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      cliente: this.editForm.get(['cliente'])!.value,
      empleado: this.editForm.get(['empleado'])!.value,
      coches: this.listaCochesComprar,
      motos: this.listaMotosComprar,
    };
  }
}
