package be.lionelh.whist.score.backend.rest.vo;

import be.lionelh.whist.score.backend.data.domain.Contract;
import be.lionelh.whist.score.backend.data.domain.Event;
import be.lionelh.whist.score.backend.data.domain.Result;

import java.util.List;

public class DrawVO {

    private Contract contract;
    private Result result;
    private List<PlayerDrawVO> players;

    private Event event;

    public DrawVO() {
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<PlayerDrawVO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDrawVO> players) {
        this.players = players;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
