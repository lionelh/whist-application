import { Routes } from '@angular/router';
import { DatabaseCounters } from './pages/database-counters/database-counters';
import { RoleManagement } from './pages/roles/pages/role-management';
import { PlayerManagement } from './pages/players/pages/player-management';
import { ContractManagementComponent } from './pages/contract/pages/contract-management';
import { ResultManagementComponent } from './pages/result/pages/result-management';

export const ADMINISTRATION_ROUTES: Routes = [
  { path: '', redirectTo: 'database-counters', pathMatch: 'full' },
  { path: 'database-counters', component: DatabaseCounters },
  { path: 'roles', component: RoleManagement },
  { path: 'players', component: PlayerManagement },
  { path: 'contracts', component: ContractManagementComponent },
  { path: 'results', component: ResultManagementComponent },
  { path: '**', redirectTo: 'database-counters' }
];



