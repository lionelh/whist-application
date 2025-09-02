import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { DatabaseCounter } from '../../../main/data/database-counter';
import { Data } from '../../../main/services/data';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'wsw-database-counters',
  imports: [CommonModule],
  templateUrl: './database-counters.html',
  styleUrl: './database-counters.css'
})
export class DatabaseCounters {
  counters$: Observable<DatabaseCounter>|undefined;

  constructor(private _dataService: Data) {}

  ngOnInit(): void {
    this.counters$ = this._dataService.retrieveDatabaseCounters();
  }
}
