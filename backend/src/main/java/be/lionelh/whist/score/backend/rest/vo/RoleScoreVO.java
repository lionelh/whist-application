package be.lionelh.whist.score.backend.rest.vo;

public class RoleScoreVO {

    private short score;
    private String roleName;

    public RoleScoreVO() {
    }

    public RoleScoreVO(String roleName, short score) {
        this.score = score;
        this.roleName = roleName;
    }

    public short getScore() {
        return score;
    }

    public void setScore(short score) {
        this.score = score;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
