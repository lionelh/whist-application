package be.lionelh.whist.score.backend.data.domain;

import be.lionelh.whist.score.backend.data.listener.DateEntityListener;
import be.lionelh.whist.score.backend.data.listener.Persistable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_PLAYERS")
@EntityListeners({ DateEntityListener.class })
public class Player implements Serializable, Persistable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_sequence")
    @SequenceGenerator(name = "player_sequence", sequenceName = "SEQ_PLAYER", allocationSize = 1)
    @Column(name = "PL_ID", scale = 20, precision = 0, unique = true, nullable = false)
    private Long id;

    @Column(name = "PL_NAME", length = 150, nullable = false, unique = true)
    private String name;

    @Column(name = "PL_CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "PL_LAST_UPDATE_DATE", nullable = false)
    private LocalDateTime lastUpdateDate;

    public Player() {
        super();
    }

    public Player(String inName) {
        this();
        this.name = inName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Player player = (Player) o;

        return new EqualsBuilder().append(id, player.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
