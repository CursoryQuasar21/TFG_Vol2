import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVenta } from '../venta.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { VentaService } from '../service/venta.service';
import { VentaDeleteDialogComponent } from '../delete/venta-delete-dialog.component';

@Component({
  selector: 'jhi-venta',
  templateUrl: './venta.component.html',
})
export class VentaComponent implements OnInit {
  ventas?: IVenta[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  //Variables necesarias para filtrar la tabla
  filtroId?: number;
  filtroTotalI?: number;
  filtroTotalF?: number;
  filtroFechaI?: Date;
  filtroFechaF?: Date;
  filtroCliente?: number;
  filtroEmpleado?: number;

  fechaIA?: string = '';
  fechaFA?: string = '';

  constructor(
    protected ventaService: VentaService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;

    this.ventaService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IVenta[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
        },
        () => {
          this.isLoading = false;
          this.onError();
        }
      );
  }

  //Metodo para filtrar la tabla de forma multiple
  filter(page?: number, dontNavigate?: boolean): void {
    if (
      this.filtroId !== undefined ||
      this.filtroTotalI !== undefined ||
      this.filtroTotalF !== undefined ||
      this.filtroFechaI !== undefined ||
      this.filtroFechaF !== undefined ||
      this.filtroCliente !== undefined ||
      this.filtroEmpleado !== undefined
    ) {
      this.isLoading = true;
      const pageToLoad: number = page ?? this.page ?? 1;
      let idA = '';
      let totalIA = '';
      let totalFA = '';
      let idCA = '';
      let idEA = '';
      if (this.filtroId !== undefined) {
        idA = this.filtroId.toString();
      }
      if (this.filtroTotalI !== undefined) {
        totalIA = this.filtroTotalI.toString();
      }
      if (this.filtroTotalF !== undefined) {
        totalFA = this.filtroTotalF.toString();
      }
      if (this.filtroCliente !== undefined) {
        idCA = this.filtroCliente.toString();
      }
      if (this.filtroEmpleado !== undefined) {
        idEA = this.filtroEmpleado.toString();
      }
      if (this.filtroFechaI !== undefined && this.filtroFechaI.toLocaleString() !== '') {
        this.fechaIA = this.filtroFechaI.toLocaleString();
        this.fechaIA = this.conversorFecha(this.fechaIA);
      } else {
        this.fechaIA = '1970-01-01T00:00:00.00Z';
      }
      if (this.filtroFechaF !== undefined && this.filtroFechaF.toLocaleString() !== '') {
        this.fechaFA = this.filtroFechaF.toLocaleString();
        this.fechaFA = this.conversorFecha(this.fechaFA);
      } else {
        const fechaAux = new Date(Date.now());
        this.fechaFA = fechaAux.getFullYear().toString() + '-';
        if (fechaAux.getMonth() < 10) {
          this.fechaFA = this.fechaFA + '0';
        }
        this.fechaFA = this.fechaFA + (fechaAux.getMonth() + 1).toString() + '-';
        if (fechaAux.getDate() < 10) {
          this.fechaFA = this.fechaFA + '0';
        }
        this.fechaFA = this.fechaFA + fechaAux.getDate().toString() + 'T';
        if (fechaAux.getHours() < 10) {
          this.fechaFA = this.fechaFA + '0';
        }
        this.fechaFA = this.fechaFA + fechaAux.getHours().toString() + ':';
        if (fechaAux.getMinutes() < 10) {
          this.fechaFA = this.fechaFA + '0';
        }
        this.fechaFA = this.fechaFA + fechaAux.getMinutes().toString() + ':';
        if (fechaAux.getSeconds() < 10) {
          this.fechaFA = this.fechaFA + '0';
        }
        this.fechaFA = this.fechaFA + fechaAux.getSeconds().toString() + '.00Z';
      }
      this.ventaService
        .filter({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          id: idA,
          totalI: totalIA,
          totalF: totalFA,
          fechaI: this.fechaIA,
          fechaF: this.fechaFA,
          idC: idCA,
          idE: idEA,
        })
        .subscribe(
          (res: HttpResponse<IVenta[]>) => {
            this.isLoading = false;
            this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
          },
          () => {
            this.isLoading = false;
            this.onError();
          }
        );
    }
  }

  ngOnInit(): void {
    this.handleNavigation();
  }

  trackId(index: number, item: IVenta): number {
    return item.id!;
  }

  delete(venta: IVenta): void {
    const modalRef = this.modalService.open(VentaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.venta = venta;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  conversorFecha(cadena: string): string {
    //27/11/2021 20:18:14
    const exp1 = new RegExp(' ', 'g');
    const fecha = cadena.split(' ', 2);
    const exp2 = new RegExp('/', 'g');
    const anios = fecha[0].split(exp2);
    const anios2 = anios[0] + '-' + anios[1] + '-' + anios[2];
    const fechaFinal = fecha[0] + ':00.00Z';
    return fechaFinal; //2019-10-01T08:25:24.00Z
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    });
  }

  protected onSuccess(data: IVenta[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/venta'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.ventas = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
