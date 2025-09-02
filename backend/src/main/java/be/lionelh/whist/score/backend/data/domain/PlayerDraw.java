package be.lionelh.whist.score.backend.data.domain;

import be.lionelh.whist.score.backend.data.domain.pk.PlayerDrawPK;
import be.lionelh.whist.score.backend.data.domain.pk.ResultRolePK;
import be.lionelh.whist.score.backend.data.listener.DateEntityListener;
import be.lionelh.whist.score.backend.data.listener.Persistable;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_PLAYER_DRAWS")
@EntityListeners({ DateEntityListener.class })
public class PlayerDraw implements Serializable, Persistable {

    @EmbeddedId
    private PlayerDrawPK id;

    @ManyToOne(optional = false, targetEntity = Player.class)
    @MapsId("playerID")
    @JoinColumn(name = "PLD_PL_ID", referencedColumnName = "PL_ID")
    Player player;

    @ManyToOne(optional = false, targetEntity = Draw.class)
    @MapsId("drawID")
    @JoinColumn(name = "PLD_DRW_ID", referencedColumnName = "DRW_ID")
    Draw draw;

    @ManyToOne(optional = false, targetEntity = Role.class)
    @JoinColumn(name = "PLD_ROL_ID", referencedColumnName = "ROL_ID")
    Role role;

    @Column(name = "PLD_DRAW_SCORE", scale = 4, precision = 0, nullable = false)
    private short drawScore;

    @Column(name = "PLD_EVENT_SCORE", scale = 5, precision = 0, nullable = false)
    private short eventScore;

    @Column(name = "PLD_CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "PLD_LAST_UPDATE_DATE", nullable = false)
    private LocalDateTime lastUpdateDate;

    @Column(name = "PLD_DEALER", scale = 1, precision = 0, nullable = false)
    private int intDealer;

    public PlayerDraw() {
        super();
    }

    public PlayerDraw(Player player, Draw draw, short drawScore, short eventScore, Role inRole) {
        this.player = player;
        this.draw = draw;
        this.drawScore = drawScore;
        this.eventScore = eventScore;
        this.id = new PlayerDrawPK(this.player, this.draw);
        this.role = inRole;
    }

    public PlayerDrawPK getId() {
        return id;
    }

    public void setId(PlayerDrawPK id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Draw getDraw() {
        return draw;
    }

    public void setDraw(Draw draw) {
        this.draw = draw;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isDealer() {
        return (this.intDealer == 1);
    }

    public void setDealer(boolean dealer) {
        if (dealer) {
            this.intDealer = 1;
        } else {
            this.intDealer = 0;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("player", player)
                .append("draw", draw)
                .append("drawScore", drawScore)
                .append("eventScore", eventScore)
                .toString();
    }
}
