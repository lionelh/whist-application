import { FormArray, FormControl } from '@angular/forms';

export interface ResultCreationForm {
  name: FormControl<string>;
  contract: FormControl<number>;
  //roles: FormArray<FormControl<number>>;
  [key: string]: FormControl<any>;
}
