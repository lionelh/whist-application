package be.lionelh.whist.score.backend.rest.vo;

import be.lionelh.whist.score.backend.data.domain.Player;

public class PlayerDrawVO {

    private String playerName;
    private short drawScore;
    private short eventScore;
    private String roleName;

    private boolean dealer;

    public PlayerDrawVO() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public short getDrawScore() {
        return drawScore;
    }

    public void setDrawScore(short drawScore) {
        this.drawScore = drawScore;
    }

    public short getEventScore() {
        return eventScore;
    }

    public void setEventScore(short eventScore) {
        this.eventScore = eventScore;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isDealer() {
        return dealer;
    }

    public void setDealer(boolean dealer) {
        this.dealer = dealer;
    }
}
