import { FormArray, FormControl } from "@angular/forms";

export interface EventCreationFrom {
  place: FormControl<string>;
  players: FormArray<FormControl<number>>;
}
