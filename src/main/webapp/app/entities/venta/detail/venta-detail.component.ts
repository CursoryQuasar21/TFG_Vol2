import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ICoche } from 'app/entities/coche/coche.model';
import { IMoto } from 'app/entities/moto/moto.model';
import { CocheService } from 'app/entities/coche/service/coche.service';
import { MotoService } from 'app/entities/moto/service/moto.service';
import { IVenta, Venta } from '../venta.model';

@Component({
  selector: 'jhi-venta-detail',
  templateUrl: './venta-detail.component.html',
})
export class VentaDetailComponent implements OnInit {
  venta: IVenta | null = null;
  //Coleccion de coches y motos para poder mostrar los coches y motos disponibles
  cochesSharedCollection: ICoche[] = [];
  motosSharedCollection: IMoto[] = [];

  constructor(protected activatedRoute: ActivatedRoute, protected cocheService: CocheService, protected motoService: MotoService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ venta }) => {
      this.venta = venta;
    });
    if (this.venta !== null) {
      this.loadCoches(this.venta);
      this.loadMotos(this.venta);
    }
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
        if (coche.venta?.id === venta.id) {
          //El coche no esta asigando a una venta y por tanto se puede vender
          this.cochesSharedCollection.push(coche);
        }
      });
    });
  }
  //Metodo Agregado
  public loadMotos(venta: Venta): void {
    //Funcion que nos devuelve todos las motos y que enfuncion de estos modifica la coleccion de motos
    this.motoService.query().subscribe((res: HttpResponse<IMoto[]>) => {
      res.body?.forEach((moto: IMoto) => {
        //Condicion que determina si la moto esta asignada a una venta
        if (moto.venta?.id === venta.id) {
          //La moto no esta asigando a una venta y por tanto se puede vender
          this.motosSharedCollection.push(moto);
        }
      });
    });
  }
  previousState(): void {
    window.history.back();
  }
}
