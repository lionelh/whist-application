import { FormControl } from "@angular/forms";

export type DrawCreationForm = {
  dealer: FormControl<string>;
  contract: FormControl<string>;
  result: FormControl<string>;
} & {
  [playerName: string]: FormControl<string>;
};
