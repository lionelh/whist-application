import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Game } from '../../data/game';
import { Data } from '../../services/data';
import { Player } from '../../data/player';
import { Validators, FormBuilder, ReactiveFormsModule, FormGroup, FormControl, FormArray } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { EventCreationFrom } from './forms/Event-creation';

@Component({
  selector: 'wsw-home',
  imports: [DatePipe, RouterLink, ReactiveFormsModule, CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {
  private cdr = inject(ChangeDetectorRef);
  private _dataService = inject(Data);
  private _fb = inject(FormBuilder);

  events$?: Observable<Game[]>;
  dbPlayers: Player[] = [];
  creationForm: FormGroup<EventCreationFrom>;

  constructor() {
    this.creationForm = this._fb.group<EventCreationFrom>({
      place: this._fb.control('', {
        nonNullable: true,
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(150)],
        updateOn: 'blur'
      }),
      players: this._fb.array<FormControl<number>>([], {
        validators: [Validators.required]
      })
    });
  }

  ngOnInit(): void {
    this.events$ = this._dataService.findAllEvents();
    this._dataService.findAllPlayers().subscribe((data: Player[]) => {
      this.dbPlayers = data;
    });
  }

  get place(): FormControl<string> {
    return this.creationForm.get('place') as FormControl<string>;
  }

  get players(): FormArray<FormControl<number>> {
    return this.creationForm.get('players') as FormArray<FormControl<number>>;
  }


  onSubmit(): void {
    if (this.creationForm.valid) {
      const e: Game = {
      place: this.place.value,
      players: this.players.controls
        .map(ctrl => this.dbPlayers.find(p => p.id === ctrl.value))
        .filter((p): p is Player => p !== undefined)
    };

      this._dataService.createEvent(e).subscribe(() => {
        this.events$ = this._dataService.findAllEvents();
        this.creationForm.reset({ place: ''});
        this.players.clear();
        this.cdr.detectChanges();
      });


    }
  }

  onPlayerCheckboxChange(e: Event, playerId: number) {
    const input = e.target as HTMLInputElement;
    const index = this.players.controls.findIndex(ctrl => ctrl.value === playerId);

    if (input.checked && index === -1) {
      this.players.push(this._fb.control(playerId, { nonNullable: true }));
    } else if (!input.checked && index !== -1) {
      this.players.removeAt(index);
    }
  }

  isPlayerSelected(playerId: number): boolean {
    return this.players.controls.some(ctrl => ctrl.value === playerId);
  }

  closeEvent(inId: number | undefined) {
    this._dataService.closeEvent(inId).subscribe(() => {
      this.events$ = this._dataService.findAllEvents();
      this.cdr.detectChanges();
    });
  }
}
