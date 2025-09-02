package be.lionelh.whist.score.backend.data.domain.pk;

import be.lionelh.whist.score.backend.data.domain.Draw;
import be.lionelh.whist.score.backend.data.domain.Player;
import be.lionelh.whist.score.backend.data.domain.Result;
import be.lionelh.whist.score.backend.data.domain.Role;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PlayerDrawPK implements Serializable {

    private Long playerID;
    private Long drawID;

    public PlayerDrawPK() {
        super();
    }

    public PlayerDrawPK(Long playerID, Long drawID) {
        this.playerID = playerID;
        this.drawID = drawID;
    }

    public PlayerDrawPK(Player inPlayer, Draw inDraw) {
        this.playerID = inPlayer.getId();
        this.drawID = inDraw.getId();
    }

    public Long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Long roleID) {
        this.playerID = roleID;
    }

    public Long getDrawID() {
        return drawID;
    }

    public void setDrawID(Long resultID) {
        this.drawID = resultID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        PlayerDrawPK that = (PlayerDrawPK) o;

        return new EqualsBuilder().append(playerID, that.playerID).append(drawID, that.drawID).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(playerID).append(drawID).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("playerID", playerID)
                .append("drawID", drawID)
                .toString();
    }
}
