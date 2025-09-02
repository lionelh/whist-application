import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { Data } from '../../../../main/services/data';
import { UniqueRoleNameValidator } from '../validators/unique-role-name';
import { Role } from '../../../../main/data/role';
import { CommonModule } from '@angular/common';
import { RoleCreationForm } from '../forms/role-creation';

@Component({
  selector: 'wsw-role-management',
  imports: [ ReactiveFormsModule, CommonModule ],
  templateUrl: './role-management.html',
  styleUrls: ['./role-management.css']
})
export class RoleManagement {
  private cdr = inject(ChangeDetectorRef);
  private _dataService = inject(Data);
  private _uniqueRoleNameValidator: UniqueRoleNameValidator = inject(UniqueRoleNameValidator);
  private _fb: FormBuilder = inject(FormBuilder);

  roles$: Observable<Role[]>|undefined;
  creationForm: FormGroup<RoleCreationForm>;

  constructor() {
    this.creationForm = this._fb.group<RoleCreationForm>({
      name: this._fb.nonNullable.control({value: '', disabled: false }, { validators: [ Validators.required, Validators.minLength(2), Validators.maxLength(150) ], asyncValidators: [ this._uniqueRoleNameValidator.validate.bind(this._uniqueRoleNameValidator) ], updateOn: "blur" }),
    });
  }

  ngOnInit(): void {
    this.roles$ = this._dataService.findAllRoles();
  }

  get name() { return this.creationForm?.get('name'); }

  onSubmit(): void {
    if (this.creationForm?.valid) {
      let r: Role = { name: this.creationForm.getRawValue().name };
      this._dataService.createRole(r).subscribe(
        () => {
          this.roles$ = this._dataService.findAllRoles();
          this.cdr.detectChanges();
        }
      );
      this.creationForm.reset();
    }
  }
}
