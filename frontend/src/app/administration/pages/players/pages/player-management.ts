import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { Data } from '../../../../main/services/data';
import { UniquePlayerNameValidator } from '../validators/unique-player-name';
import { Player } from '../../../../main/data/player';
import { AsyncPipe } from '@angular/common';
import { PlayerCreationForm } from '../forms/player-creation';

@Component({
  selector: 'wsw-player-management',
  imports: [ ReactiveFormsModule, AsyncPipe ],
  templateUrl: './player-management.html',
  styleUrls: ['./player-management.css']
})
export class PlayerManagement {
  private cdr = inject(ChangeDetectorRef);
  private _dataService = inject(Data);
  private _uniquePlayerNameValidator: UniquePlayerNameValidator = inject(UniquePlayerNameValidator);
  private _fb: FormBuilder = inject(FormBuilder);

  players$: Observable<Player[]>|undefined;
  creationForm: FormGroup<PlayerCreationForm>;

  constructor() {
    this.creationForm = this._fb.group<PlayerCreationForm>({
      name: this._fb.nonNullable.control({value: '', disabled: false }, { validators: [ Validators.required, Validators.minLength(2), Validators.maxLength(150) ], asyncValidators: [ this._uniquePlayerNameValidator.validate.bind(this._uniquePlayerNameValidator) ], updateOn: "blur" }),
    });
  }

  ngOnInit(): void {
    this.players$ = this._dataService.findAllPlayers();
  }

  get name() { return this.creationForm?.get('name'); }

  onSubmit(): void {
    if (this.creationForm?.valid) {
      let r: Player = { name: this.creationForm.getRawValue().name };
      this._dataService.createPlayer(r).subscribe(
        () => {
          this.players$ = this._dataService.findAllPlayers();
          this.cdr.detectChanges();
        }
      );
      this.creationForm.reset();
    }
  }
}
