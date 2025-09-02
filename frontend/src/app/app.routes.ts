import { Routes } from '@angular/router';
import { Home } from './main/pages/home/home';

export const routes: Routes = [

  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {
    path: 'event/:id',
    loadComponent: () =>
      import('./main/pages/event/event').then(m => m.Event)
  },
/*
  { path: 'players', loadChildren: () => import('./player/player.module').then(m => m.PlayerModule) },
  { path: 'roles', loadChildren: () => import('./role/role.module').then(m => m.RoleModule) },
  { path: 'contracts', loadChildren: () => import('./contract/contract.module').then(m => m.ContractModule) },
  { path: 'results', loadChildren: () => import('./result/result.module').then(m => m.ResultModule) },
*/
  {
    path: 'administration',
    loadChildren: () =>
      import('./administration/administration.routes').then((m) => m.ADMINISTRATION_ROUTES),
  },
  { path: '**', component: Home },
];
