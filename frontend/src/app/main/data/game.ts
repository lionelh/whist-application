import { Player } from "./player";

export interface Game {
    id?: number;
    eventDate?: Date;
    place?: string;
    status?: string;
    players?: Player[];
}