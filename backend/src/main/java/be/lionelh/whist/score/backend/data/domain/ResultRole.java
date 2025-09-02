package be.lionelh.whist.score.backend.data.domain;

import be.lionelh.whist.score.backend.data.domain.pk.ResultRolePK;
import be.lionelh.whist.score.backend.data.listener.DateEntityListener;
import be.lionelh.whist.score.backend.data.listener.Persistable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_RESULT_ROLES")
@EntityListeners({ DateEntityListener.class })
public class ResultRole implements Serializable, Persistable {

    @EmbeddedId
    private ResultRolePK id;

    @ManyToOne(optional = false, targetEntity = Result.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH })
    @MapsId("resultID")
    @JoinColumn(name = "RER_RES_ID", referencedColumnName = "RES_ID")
    Result result;

    @ManyToOne(optional = false, targetEntity = Role.class)
    @MapsId("roleID")
    @JoinColumn(name = "RER_ROL_ID", referencedColumnName = "ROL_ID")
    Role role;

    @Column(name = "RER_SCORE", scale = 4, precision = 0, nullable = false)
    private short score;

    @Column(name = "RER_CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "RER_LAST_UPDATE_DATE", nullable = false)
    private LocalDateTime lastUpdateDate;

    public ResultRole() {
        super();
    }

    public ResultRole(Result result, Role role, short score) {
        this.result = result;
        this.role = role;
        this.score = score;
        this.id = new ResultRolePK(this.role, this.result);
    }

    public ResultRolePK getId() {
        return id;
    }

    public void setId(ResultRolePK id) {
        this.id = id;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public short getScore() {
        return score;
    }

    public void setScore(short score) {
        this.score = score;
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

        ResultRole that = (ResultRole) o;

        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("result", result)
                .append("role", role)
                .append("score", score)
                .toString();
    }
}
