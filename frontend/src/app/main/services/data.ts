import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Player } from '../data/player';
import { Role } from '../data/role';
import { Contract } from '../data/contract';
import { ResultVO } from '../data/resultVO';
import { DrawVO } from '../data/drawVO';
import { DatabaseCounter } from '../data/database-counter';
import { Game } from '../data/game';

@Injectable({
  providedIn: 'root'
})
export class Data {

  constructor(private _http: HttpClient) { }

  findAllPlayers(): Observable<Player[]> {
    let apiURL: string = '/api/players';
    return this._http.get<Player[]>(apiURL);
  }

  playerNameExists(inName: string): Observable<boolean> {
    let apiURL: string = '/api/players/' + inName + '/exists';
    return this._http.get<boolean>(apiURL);
  }

  createPlayer(inPlayer: Player): Observable<Player> {
    let apiURL: string = '/api/players';
    return this._http.post<Player>(apiURL, inPlayer);
  }

  findAllRoles(): Observable<Role[]> {
    let apiURL: string = '/api/roles';
    return this._http.get<Role[]>(apiURL);
  }

  roleNameExists(inName: string): Observable<boolean> {
    let apiURL: string = '/api/roles/' + inName + '/exists';
    return this._http.get<boolean>(apiURL);
  }

  createRole(inRole: Role): Observable<Player> {
    let apiURL: string = '/api/roles';
    return this._http.post<Role>(apiURL, inRole);
  }

  findAllContracts(): Observable<Contract[]> {
    let apiURL: string = '/api/contracts';
    return this._http.get<Contract[]>(apiURL);
  }

  contractExistsByNameAndNumberOfPlayers(inName: string, inNumberOfPlayers: number): Observable<boolean> {
    let apiURL: string = '/api/contracts/' + inName + '/' + inNumberOfPlayers + '/exists';
    return this._http.get<boolean>(apiURL);
  }

  createContract(inContract: Contract): Observable<Contract> {
    let apiURL: string = '/api/contracts';
    return this._http.post<Contract>(apiURL, inContract);
  }

  findallResults(): Observable<ResultVO[]> {
    let apiURL: string = '/api/results';
    return this._http.get<ResultVO[]>(apiURL);
  }

  createResult(inResult: ResultVO): Observable<ResultVO> {
    let apiURL: string = '/api/results';
    return this._http.post<ResultVO>(apiURL, inResult);
  }

  findAllEvents(): Observable<Game[]> {
    let apiURL: string = '/api/events';
    return this._http.get<Game[]>(apiURL);
  }

  createEvent(inEvent: Game): Observable<Game> {
    let apiURL: string = '/api/events';
    return this._http.post<Game>(apiURL, inEvent);
  }
  findEventById(inId: number): Observable<Game> {
    let apiURL: string = '/api/events/' + inId;
    return this._http.get<Game>(apiURL);
  }

  retrieveEventDetails(inId?: number): Observable<DrawVO[]> {
    let apiURL: string = '/api/events/' + inId + '/details';
    return this._http.get<DrawVO[]>(apiURL);
  }

  findContractsByNumberOfPlayers(inNumberOfPlayers?: number): Observable<Contract[]> {
    let apiURL: string = '/api/contracts/numberofplayers/' + inNumberOfPlayers;
    return this._http.get<Contract[]>(apiURL);
  }

  findResultsByContractId(inId: number): Observable<ResultVO[]> {
    let apiURL: string = '/api/contracts/' + inId + '/results';
    return this._http.get<ResultVO[]>(apiURL);
  }

  createDraw(inEventId: number | undefined, inDraw: DrawVO): Observable<DrawVO[]> {
    let apiURL: string = '/api/events/' + inEventId + '/draw';
    return this._http.post<DrawVO[]>(apiURL, inDraw);
  }

  closeEvent(inId: number | undefined): Observable<DrawVO[]> {
    let apiURL: string = '/api/events/' + inId + '/close';
    return this._http.post<DrawVO[]>(apiURL, {});
  }

  retrieveDatabaseCounters(): Observable<DatabaseCounter> {
    let apiURL: string = '/api/db/counters';
    return this._http.get<DatabaseCounter>(apiURL);
  }

  findContractsForEvent(inEventId: number): Observable<Contract[]> {
    let apiURL: string = '/api/events/' + inEventId + '/contracts';
    return this._http.get<Contract[]>(apiURL);
  }
}
