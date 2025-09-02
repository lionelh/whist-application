package be.lionelh.whist.score.backend.rest.vo;

public class DatabaseCounter {

    private long contracts;
    private long contractRoles;
    private long draws;
    private long events;
    private long players;
    private long playerDraws;
    private long results;
    private long resultRoles;
    private long roles;
    private long eventPlayers;

    private long totalRecords;

    public DatabaseCounter() {
    }

    public long getContracts() {
        return contracts;
    }

    public void setContracts(long contracts) {
        this.contracts = contracts;
    }

    public long getContractRoles() {
        return contractRoles;
    }

    public void setContractRoles(long contractRoles) {
        this.contractRoles = contractRoles;
    }

    public long getDraws() {
        return draws;
    }

    public void setDraws(long draws) {
        this.draws = draws;
    }

    public long getEvents() {
        return events;
    }

    public void setEvents(long events) {
        this.events = events;
    }

    public long getPlayers() {
        return players;
    }

    public void setPlayers(long players) {
        this.players = players;
    }

    public long getPlayerDraws() {
        return playerDraws;
    }

    public void setPlayerDraws(long playerDraws) {
        this.playerDraws = playerDraws;
    }

    public long getResults() {
        return results;
    }

    public void setResults(long results) {
        this.results = results;
    }

    public long getResultRoles() {
        return resultRoles;
    }

    public void setResultRoles(long resultRoles) {
        this.resultRoles = resultRoles;
    }

    public long getRoles() {
        return roles;
    }

    public void setRoles(long roles) {
        this.roles = roles;
    }

    public long getEventPlayers() {
        return eventPlayers;
    }

    public void setEventPlayers(long eventPlayers) {
        this.eventPlayers = eventPlayers;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
