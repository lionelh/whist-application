import { Role } from "./role";

export interface Contract {
    id?: number;
    name: string;
    numberOfPlayers: number;
    roles: Role[];
}