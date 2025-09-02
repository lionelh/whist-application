import { Contract } from "./contract";
import { PlayerDrawVO } from "./player-drawVO";
import { ResultVO } from "./resultVO";


export interface DrawVO {
    contract?: Contract;
    result?: ResultVO;
    players?: PlayerDrawVO[];
}