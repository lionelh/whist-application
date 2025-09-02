package be.lionelh.whist.score.backend.data.domain;

import be.lionelh.whist.score.backend.data.listener.DateEntityListener;
import be.lionelh.whist.score.backend.data.listener.Persistable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TBL_RESULTS")
@EntityListeners({ DateEntityListener.class })
public class Result implements Serializable, Persistable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_sequence")
    @SequenceGenerator(name = "result_sequence", sequenceName = "SEQ_RESULT", allocationSize = 1)
    @Column(name = "RES_ID", scale = 20, precision = 0, unique = true, nullable = false)
    private Long id;

    @Column(name = "RES_NAME", length = 150, nullable = false)
    private String name;

    @Column(name = "RES_NUMBER_PLAYERS", scale = 1, precision = 0, nullable = false)
    private short numberOfPlayers;

    @ManyToOne(optional = false, targetEntity = Contract.class)
    @JoinColumn(name = "RES_CTR_ID", referencedColumnName = "CTR_ID")
    private Contract contract;

    @Column(name = "RES_CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "RES_LAST_UPDATE_DATE", nullable = false)
    private LocalDateTime lastUpdateDate;

    public Result() {
        super();
    }

    public Result(String name) {
        super();
        this.name = name;
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

    public short getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(short numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
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

        Result contract = (Result) o;

        return new EqualsBuilder().append(id, contract.id).isEquals();
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
                .append("numberOfPlayers", numberOfPlayers)
                .append("contract", contract)
                .toString();
    }
}
