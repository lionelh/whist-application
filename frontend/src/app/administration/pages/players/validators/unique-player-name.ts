import { inject, Injectable } from "@angular/core";
import { AbstractControl, AsyncValidator, ValidationErrors } from "@angular/forms";
import { catchError, map, Observable, of } from "rxjs";
import { Data } from "../../../../main/services/data";

@Injectable({ providedIn: 'root' })
export class UniquePlayerNameValidator implements AsyncValidator {
  private _dataService: Data = inject(Data);

  validate(control: AbstractControl): Observable<ValidationErrors | null> {
    return this._dataService.playerNameExists(control.value).pipe(
      map(nameExists => (nameExists ? { uniquePlayerName: true } : null)),
      catchError(() => of(null))
    );
  }
}
