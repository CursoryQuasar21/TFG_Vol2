import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'empleado',
        data: { pageTitle: 'tfgVol2App.empleado.home.title' },
        loadChildren: () => import('./empleado/empleado.module').then(m => m.EmpleadoModule),
      },
      {
        path: 'cliente',
        data: { pageTitle: 'tfgVol2App.cliente.home.title' },
        loadChildren: () => import('./cliente/cliente.module').then(m => m.ClienteModule),
      },
      {
        path: 'coche',
        data: { pageTitle: 'tfgVol2App.coche.home.title' },
        loadChildren: () => import('./coche/coche.module').then(m => m.CocheModule),
      },
      {
        path: 'moto',
        data: { pageTitle: 'tfgVol2App.moto.home.title' },
        loadChildren: () => import('./moto/moto.module').then(m => m.MotoModule),
      },
      {
        path: 'venta',
        data: { pageTitle: 'tfgVol2App.venta.home.title' },
        loadChildren: () => import('./venta/venta.module').then(m => m.VentaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
