import {Component, inject, OnInit} from '@angular/core';
import { FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { Contract } from '../../../../main/data/contract';
import { ResultVO } from '../../../../main/data/resultVO';
import { Data } from '../../../../main/services/data';
import { AsyncPipe } from '@angular/common';
import {ResultCreationForm} from '../forms/result-creation';
import {ResultRequiredFieldsValidator} from '../validators/result-required-fields';

@Component({
  selector: 'wsw-result-management',
  imports: [ ReactiveFormsModule, AsyncPipe ],
  templateUrl: './result-management.html',
  styleUrls: ['./result-management.css']
})
export class ResultManagementComponent implements OnInit {
  private _dataService: Data = inject(Data);
  private _fb: NonNullableFormBuilder = inject(NonNullableFormBuilder);
  private _requiredFieldsValidator: ResultRequiredFieldsValidator = inject(ResultRequiredFieldsValidator);

  results$: Observable<ResultVO[]>|undefined;
  contracts: Contract[];
  roleScoresArray: string[];
  oldContractValue: number = -1;
  creationForm: FormGroup<ResultCreationForm>;

  constructor() {
    this.creationForm = this._fb.group<ResultCreationForm>({
      name: this._fb.control('', [Validators.required, Validators.minLength(2), Validators.maxLength(150)]),
      contract: this._fb.control(0, [Validators.required]),
    }, { validators: [ this._requiredFieldsValidator.validate.bind(this._requiredFieldsValidator) ] });
    this.contracts = [];
    this.roleScoresArray = [];
  }

  get name() { return this.creationForm.controls.name; }
  get contract() { return this.creationForm.controls.contract; }
  //get roleScores() { return this.creationForm.get('roleScores'); }

  ngOnInit(): void {
    this.results$ = this._dataService.findallResults();
    this._dataService.findAllContracts().subscribe({
      next: (data) => this.contracts = data,
      error: () => this.contracts = []
    });
  }

  onSubmit(): void {
    if (this.creationForm.valid) {
      const formValue = this.creationForm.getRawValue();
      const selectedContract = this.contracts.find(c => c.id === Number(formValue.contract));

      if (selectedContract) {
        const result: ResultVO = {
          name: formValue.name,
          contract: selectedContract,
          numberOfPlayers: selectedContract.numberOfPlayers,
          roleScores: selectedContract.roles?.map(role => ({
            roleName: role.name,
            score: Number(this.creationForm.get(role.name)?.value as number),
          })) || []
        };

        this._dataService.createResult(result).subscribe(
          (data) => {
            this.results$ = this._dataService.findallResults();
            this.resetComponent();
          }
        );
      }
    }
  }

  onContractChange(): void {
    const  currentContractId = Number(this.contract?.value);

    // first remove old controls (if needed)
    if (this.oldContractValue != -1) {
      const oldContract = this.contracts.find(c => c.id === this.oldContractValue);
      oldContract?.roles?.forEach(role => {
        (this.creationForm as FormGroup).removeControl(role.name);
      });
    }
    this.roleScoresArray = [];

    // Second add new controls
    const newContract = this.contracts.find(c => c.id === Number(currentContractId));
    if (newContract) {
      newContract.roles?.forEach(role => {
        this.creationForm.addControl(role.name, new FormControl('0', { nonNullable: true }));
        this.roleScoresArray.push(role.name);
      });
    }

    this.oldContractValue = currentContractId;
  }

  private resetComponent(): void {
    this.roleScoresArray.forEach(roleName  => { (this.creationForm as FormGroup).removeControl(roleName) });
    this.creationForm.reset();
    this.oldContractValue = -1;
    this.roleScoresArray = [];
  }
}
