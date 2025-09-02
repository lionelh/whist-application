package be.lionelh.whist.score.backend.data.domain;

import be.lionelh.whist.score.backend.data.listener.DateEntityListener;
import be.lionelh.whist.score.backend.data.listener.Persistable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TBL_EVENTS")
@EntityListeners({ DateEntityListener.class })
public class Event implements Serializable, Persistable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_sequence")
    @SequenceGenerator(name = "event_sequence", sequenceName = "SEQ_EVENT", allocationSize = 1)
    @Column(name = "EVT_ID", scale = 20, precision = 0, unique = true, nullable = false)
    private Long id;
    @Column(name = "EVT_DATE", nullable = false)
    private LocalDate eventDate;
    @Column(name = "EVT_PLACE", length = 150, nullable = false)
    private String place;
    @Column(name = "EVT_status", length = 50, nullable = false)
    private EventStatus status;
    @ManyToMany
    @JoinTable(name = "TBL_EVENT_PLAYERS", joinColumns = @JoinColumn(name = "EVP_EVT_id"), inverseJoinColumns = @JoinColumn(name = "EVP_pl__id"))
    private List<Player> players;
    @Column(name = "EVT_CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;
    @Column(name = "EVT_LAST_UPDATE_DATE", nullable = false)
    private LocalDateTime lastUpdateDate;

    public Event() {
        super();
    }

    public Event(LocalDate eventDate, String place) {
        this.eventDate = eventDate;
        this.place = place;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
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

        Event event = (Event) o;

        return new EqualsBuilder().append(id, event.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("eventDate", eventDate)
                .append("place", place)
                .append("status", this.status.getValue())
                .toString();
    }
}
