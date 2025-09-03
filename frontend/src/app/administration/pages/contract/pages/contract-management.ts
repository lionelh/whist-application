import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, UntypedFormGroup, Validators, FormArray, FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { Contract } from '../../../../main/data/contract';
import { Role } from '../../../../main/data/role';
import { UniqueContractByNameAndNumberOfPlayersValidator } from '../validators/unique-contract-name-and-number-of-players.validator';
import { Data } from '../../../../main/services/data';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'wsw-contract-management',
  imports: [ ReactiveFormsModule, CommonModule ],
  templateUrl: './contract-management.html',
  styleUrls: ['./contract-management.css']
})
export class ContractManagementComponent {
  private cdr = inject(ChangeDetectorRef);
  private _dataService: Data = inject(Data);
  private _uniqueContract: UniqueContractByNameAndNumberOfPlayersValidator = inject(UniqueContractByNameAndNumberOfPlayersValidator);
  private _fb: FormBuilder = inject(FormBuilder);
  contracts$: Observable<Contract[]>|undefined;
  rolesArray: Role[]|undefined;
  creationForm: UntypedFormGroup;

  constructor() {
    this.creationForm = this._fb.group({
      name: this._fb.nonNullable.control('', { validators: [ Validators.required, Validators.minLength(2), Validators.maxLength(150) ], asyncValidators: [ ], updateOn: "blur" }),
      numberOfPlayers: this._fb.nonNullable.control(4, { validators: [ Validators.required ], asyncValidators: [ ], updateOn: "blur" }),
      roles: this._fb.array([], [ Validators.required ])
    }, { validators: [], asyncValidators: [ this._uniqueContract ] });
  }

  ngOnInit(): void {
    this.contracts$ = this._dataService.findAllContracts();
    this._dataService.findAllRoles().subscribe(
      (data) => {
        this.rolesArray = data;
      }
    );
  }

  get name() { return this.creationForm?.get('name'); }
  get roles() { return this.creationForm?.get('roles'); }

  findRoleById(inId: number): Role|undefined {
    let found: Role|undefined = undefined;
    this.rolesArray?.forEach((item) => {
      if (item.id == inId) {
        found = item as Role;
      }
    });
    return found;
  }

  onSubmit(): void {
    if (this.creationForm?.valid) {
      let c: Contract = { name: this.creationForm?.value['name'], numberOfPlayers: this.creationForm?.value['numberOfPlayers'], roles: [] };
      const rolesTab: FormArray<FormControl<number>> = this.creationForm.get('roles') as FormArray<FormControl<number>>;
      rolesTab.controls.forEach((item) => {
        let roleId: number = item.value;
        let r: Role|undefined = this.findRoleById(roleId);
        if (r !== undefined) {
          c.roles?.push(r);
        }
      });

      this._dataService.createContract(c).subscribe(
        () => {
          this.contracts$ = this._dataService.findAllContracts();
          this.resetForm();
        }
      );

    }
  }

  private resetForm(): void {
    this.creationForm.reset({
      name: '',
      numberOfPlayers: 4,
    });
    (this.roles as FormArray<FormControl<number>>).clear();
    this.cdr.detectChanges();
  }

  isRoleSelected(roleId: number): boolean {
    const rolesArray = this.creationForm.get('roles') as FormArray<FormControl<number>>;
    return rolesArray.controls.some(control => control.value === roleId);
  }

  onRoleCheckboxChange(e: Event) {
    let eventTarget: HTMLInputElement|null = e.target as HTMLInputElement;
    const rolesArray: FormArray<FormControl<number>> = this.creationForm.get('roles') as FormArray<FormControl<number>>;
    if (eventTarget.checked) {
      const newValue: number = +eventTarget.value;
      rolesArray.push(new FormControl<number>(newValue, { nonNullable: true }));
    } else {
      let i: number = 0;
      rolesArray.controls.forEach((item) => {
        if (item.value == +eventTarget?.value) {
          rolesArray.removeAt(i);
          return;
        }
        i++;
      });
    }
  }
}
