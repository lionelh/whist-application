import { inject, Injectable } from "@angular/core";
import { AbstractControl, AsyncValidator, FormControl, ValidationErrors } from "@angular/forms";
import { catchError, map, Observable, of } from "rxjs";
import { Data } from "../../../../main/services/data";


@Injectable({ providedIn: 'root' })
export class UniqueContractByNameAndNumberOfPlayersValidator implements AsyncValidator {

  private _dataService: Data = inject(Data);

  validate(group: AbstractControl): Observable<ValidationErrors | null> {
    let numberOfPlayersControl: AbstractControl|null = group.get('numberOfPlayers');
    let nameControl: AbstractControl|null = group.get('name');
    return this._dataService.contractExistsByNameAndNumberOfPlayers(nameControl?.value, numberOfPlayersControl?.value).pipe(
      map(contractExists => (contractExists ? { uniqueContract: true } : null)),
      catchError(() => of(null))
    );
  }
}
