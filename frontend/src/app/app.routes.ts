import { Routes } from '@angular/router';
import { Home } from './main/pages/home/home';

export const routes: Routes = [

  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {
    path: 'event/:id',
    loadComponent: () =>
      import('./main/pages/event/event').then(m => m.Event)
  },
  {
    path: 'administration',
    loadChildren: () =>
      import('./administration/administration.routes').then((m) => m.ADMINISTRATION_ROUTES),
  },
  { path: '**', component: Home },
];
