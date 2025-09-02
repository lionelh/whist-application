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
@Table(name = "TBL_CONTRACTS")
@EntityListeners({ DateEntityListener.class })
public class Contract implements Serializable, Persistable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_sequence")
    @SequenceGenerator(name = "contract_sequence", sequenceName = "SEQ_CONTRACT", allocationSize = 1)
    @Column(name = "CTR_ID", scale = 20, precision = 0, unique = true, nullable = false)
    private Long id;

    @Column(name = "CTR_NAME", length = 150, nullable = false)
    private String name;

    @Column(name = "CTR_NUMBER_PLAYERS", scale = 1, precision = 0, nullable = false)
    private short numberOfPlayers;

    @ManyToMany
    @JoinTable(name = "TBL_CONTRACT_ROLES", joinColumns = @JoinColumn(name = "CRO_CTR_ID"), inverseJoinColumns = @JoinColumn(name = "CRO_ROL_ID"))
    private List<Role> roles;

    @Column(name = "CTR_CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "CTR_LAST_UPDATE_DATE", nullable = false)
    private LocalDateTime lastUpdateDate;

    public Contract() {
        super();
    }

    public Contract(String name) {
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

        Contract contract = (Contract) o;

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
                .append("roles", roles)
                .toString();
    }
}
