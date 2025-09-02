import { inject, Injectable } from "@angular/core";
import { AbstractControl, AsyncValidator, ValidationErrors } from "@angular/forms";
import { catchError, map, Observable, of } from "rxjs";
import { Data } from "../../../../main/services/data";


@Injectable({ providedIn: 'root' })
export class UniqueRoleNameValidator implements AsyncValidator {
  private _dataService = inject(Data);

  validate(control: AbstractControl): Observable<ValidationErrors | null> {
    return this._dataService.roleNameExists(control.value).pipe(
      map(nameExists => (nameExists ? { uniqueRoleName: true } : null)),
      catchError(() => of(null))
    );
  }
}
