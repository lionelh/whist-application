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

    // 4. Calculer le seuil (N-1)
    const totalRoles = scoreKeys.length;
    const requiredNonZero = totalRoles > 0 ? totalRoles - 1 : 0;

    // 5. Validation finale
    if (totalRoles > 0 && nonZeroScoresCount < requiredNonZero) {
      return {
        insufficientScores: {
          required: requiredNonZero,
          actual: nonZeroScoresCount,
          total: totalRoles
        }
      };
    }

    return null;
  }
}
