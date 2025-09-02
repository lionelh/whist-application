import { Routes } from '@angular/router';
import { DatabaseCounters } from './pages/database-counters/database-counters';


export const ADMINISTRATION_ROUTES: Routes = [
  { path: '', redirectTo: 'database-counters', pathMatch: 'full' },
  { path: 'database-counters', component: DatabaseCounters },
  { path: '**', redirectTo: 'database-counters' }
];



