import { Injectable } from "@angular/core";
import { AbstractControl, ValidationErrors, Validator, FormGroup } from "@angular/forms";

@Injectable({ providedIn: 'root' })
export class ResultRequiredFieldsValidator implements Validator {

  validate(group: AbstractControl): ValidationErrors | null {
    const formGroup = group as FormGroup;
    const name = formGroup.get('name')?.value;
    const contractId = formGroup.get('contract')?.value;

    // 1. On ne valide les scores que si le contrat est présent
    if (!contractId || contractId === 0) {
      return { incomplete: true };
    }

    // 2. Extraire les contrôles de scores (dynamiques)
    const controls = formGroup.controls;
    const scoreKeys = Object.keys(controls).filter(
      (key) => key !== 'name' && key !== 'contract'
    );

    // 3. Compter les scores non nuls
    const nonZeroScoresCount = scoreKeys.filter((key) => {
      const value = controls[key].value;
      // On vérifie que ce n'est pas nul, pas vide et différent de 0
      return value !== null && value !== '' && Number(value) !== 0;
    }).length;

    // 4. Validation finale
    if (nonZeroScoresCount === 0) {
      return { insufficientScores: true }
    }

    let totalScore: number = 0;
    scoreKeys.forEach(key => {
      const value: number = controls[key].value;
      totalScore += value;
    });

    if (totalScore !== 0) {
      return { badScoresSum: true }
    }

    return null;
  }
}
