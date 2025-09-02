import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Data } from '../../services/data';
import { Game } from '../../data/game';
import { Contract } from '../../data/contract';
import { DrawVO } from '../../data/drawVO';
import { CommonModule, DatePipe } from '@angular/common';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, UntypedFormBuilder, UntypedFormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { ResultVO } from '../../data/resultVO';
import { Role } from '../../data/role';
import { PlayerDrawVO } from '../../data/player-drawVO';
import { Observable, of, switchMap, tap } from 'rxjs';
import { DrawCreationForm } from './forms/draw-cretation';

@Component({
  selector: 'wsw-event',
  standalone: true,
  imports: [DatePipe, CommonModule, ReactiveFormsModule],
  templateUrl: './event.html',
  styleUrls: ['./event.css']
})
export class Event {
  private _dataService = inject(Data);
  private route = inject(ActivatedRoute);
  private _fb = inject(FormBuilder);
  private cdr = inject(ChangeDetectorRef);
  private sub: any;
  id?: number;
  event: Game | undefined;
  draws$: Observable<DrawVO[]> = of<DrawVO[]>([]);;
  creationForm: FormGroup<DrawCreationForm>;
  contracts: Contract[] | undefined;
  oldContractValue: number = -1;
  playerRoleArray: string[];
  results: ResultVO[];
  roles: Role[];

  constructor() {
    this.playerRoleArray = [];
    this.results = [];
    this.roles = [];
    this.creationForm = this._fb.group({
      contract: ['', [Validators.required], [], { updateOn: "blur", nonNullable: true }],
      result: ['', [Validators.required], [], { updateOn: "blur", nonNullable: true }],
      dealer: ['', [], [], { updateOn: "blur", nonNullable: true }]
    }) as FormGroup<DrawCreationForm>;
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = +params['id']; // (+) converts string 'id' to a number
      this.draws$ = this._dataService.findEventById(this.id).pipe(
        tap((data) => {
          this.event = { ...data, eventDate: data.eventDate ? new Date(data.eventDate) : undefined };
        }),
        switchMap((data) => this._dataService.retrieveEventDetails(data.id))
      );

      this._dataService.findContractsForEvent(this.id).subscribe(
        (data: Contract[]) => {
          this.contracts = data;
        }
      );
    });
  }

  get contract() { return this.creationForm.get('contract'); }
  get result() { return this.creationForm.get('result'); }
  get dealer() { return this.creationForm.get('dealer'); }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
  computeColspan(inLength: number | undefined): number {
    if (inLength !== undefined) {
      return (2 + inLength);
    } else {
      return 2;
    }
  }

  onSubmit(): void {
    if (this.creationForm?.valid) {
      let d: DrawVO = {};
      this.contracts?.forEach(ctr => {
        if (this.oldContractValue == ctr.id) {
          d.contract = ctr;
        }
      });
      let resultId = +this.result!.value;
      this.results?.forEach(res => {
        if (resultId == res.id) {
          d.result = res;
        }
      });
      d.players = [];
      this.event?.players?.forEach(p => {
        const pdVO: PlayerDrawVO = { playerName: p.name, roleName: this.creationForm.get(p.name)?.value, eventScore: 0, drawScore: 0 };
        if (pdVO.roleName === 'Mort' || (this.event?.players?.length == 4 && p.name == this.dealer?.value)) {
          pdVO.dealer = true;
        } else {
          pdVO.dealer = false;
        }
        d.players?.push(pdVO);
      });
      this._dataService.createDraw(this.event?.id, d).subscribe(
        (data: DrawVO[]) => {
          this.creationForm.reset();
          this.oldContractValue = -1;
          this.playerRoleArray = [];
          this.results = [];
          this.roles = [];
          this.draws$ = of(data);
          if (this.event) {
            this.event.status = "IN_PROGRESS";
          }
        }
      );
    }
  }

  onContractChange(): void {
    // first remove old controls (if needed)
    if (this.oldContractValue != -1) {
      this.event?.players?.forEach(p => {
        //const form = this.creationForm as unknown as FormGroup<{ [key: string]: FormControl<string> }>;
        const form = this.creationForm as unknown as FormGroup<{ [key: string]: AbstractControl<any, any, any> }>;

        if (form.contains(p.name)) {
          form.removeControl(p.name);
      }
      });
    }
    this.playerRoleArray = [];
    this.results = [];
    this.roles = [];
    // Second add new controls
    let contractId: number = +this.contract!.value;
    this._dataService.findResultsByContractId(contractId).subscribe(
      (data: ResultVO[]) => {
        const form = this.creationForm as unknown as FormGroup<{ [key: string]: AbstractControl<any, any, any> }>;
        this.results = data;
        this.contracts?.forEach(ctr => {
          if (contractId == ctr.id) {
            this.roles = ctr.roles;
            this.event?.players?.forEach(p => {
              form.addControl(p.name, new FormControl<string>(this.roles[0].name));
              this.playerRoleArray.push(p.name);
            });
          }
        });
        this.oldContractValue = contractId;
        this.cdr.detectChanges();
      }
    );
  }

  closeEvent(inId: number | undefined) {
    this._dataService.closeEvent(inId).subscribe(
      () => {
        if (this.event) {
          this.event.status = 'FINISHED';
          this.cdr.detectChanges();
        }
      }
    );
  }
}
