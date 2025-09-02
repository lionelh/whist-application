package be.lionelh.whist.score.backend.data.domain.pk;

import be.lionelh.whist.score.backend.data.domain.Result;
import be.lionelh.whist.score.backend.data.domain.Role;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ResultRolePK implements Serializable {

    private Long roleID;
    private Long resultID;

    public ResultRolePK() {
        super();
    }

    public ResultRolePK(Long roleID, Long resultID) {
        this.roleID = roleID;
        this.resultID = resultID;
    }

    public ResultRolePK(Role inRole, Result inResult) {
        this.roleID = inRole.getId();
        this.resultID = inResult.getId();
        ;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public Long getResultID() {
        return resultID;
    }

    public void setResultID(Long resultID) {
        this.resultID = resultID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (getClass() != o.getClass())
            return false;

        ResultRolePK that = (ResultRolePK) o;

        return new EqualsBuilder().append(roleID, that.roleID).append(resultID, that.resultID).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(roleID).append(resultID).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("roleID", roleID)
                .append("resultID", resultID)
                .toString();
    }
}
