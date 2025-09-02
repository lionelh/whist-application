import { Contract } from "./contract";
import { RoleScore } from "./role-scoreVO";

export interface ResultVO {
    id?: number;
    contract?: Contract;
    numberOfPlayers?: number;
    name?: string;
    roleScores?: RoleScore[];
}