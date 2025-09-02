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
@Table(name = "TBL_DRAWS")
@EntityListeners({ DateEntityListener.class })
public class Draw implements Serializable, Persistable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "draw_sequence")
    @SequenceGenerator(name = "draw_sequence", sequenceName = "SEQ_DRAW", allocationSize = 1)
    @Column(name = "DRW_ID", scale = 20, precision = 0, unique = true, nullable = false)
    private Long id;

    @ManyToOne(optional = false, targetEntity = Event.class)
    @JoinColumn(nullable = false, name = "DRW_EVT_ID", referencedColumnName = "EVT_ID")
    private Event event;

    @ManyToOne(optional = false, targetEntity = Contract.class)
    @JoinColumn(nullable = false, name = "DRW_CTR_ID", referencedColumnName = "CTR_ID")
    private Contract contract;

    @ManyToOne(optional = false, targetEntity = Result.class)
    @JoinColumn(nullable = false, name = "DRW_RES_ID", referencedColumnName = "RES_ID")
    private Result result;

    @Column(name = "DRW_CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "DRW_LAST_UPDATE_DATE", nullable = false)
    private LocalDateTime lastUpdateDate;

    public Draw() {
        super();
    }

    public Draw(Event inEvent, Contract inContract) {
        super();
        this.contract = inContract;
        this.event = inEvent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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

        Draw draw = (Draw) o;

        return new EqualsBuilder().append(id, draw.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("event", event)
                .append("contract", contract)
                .toString();
    }
}
