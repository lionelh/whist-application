import { FormArray, FormControl } from '@angular/forms';

export interface ContractCreationForm {
  name: FormControl<string>;
  numberOfPlayers: FormControl<number>;
  roles: FormArray<FormControl<number>>;
}
