package be.lionelh.whist.score.backend.rest.vo;

import be.lionelh.whist.score.backend.data.domain.Contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultVO implements Serializable {

    private Long id;
    private String name;
    private Contract contract;
    private short numberOfPlayers;
    private List<RoleScoreVO> roleScores;

    public ResultVO() {
    }

    public ResultVO(String inName, Contract inContract) {
        this.name = inName;
        this.contract = inContract;
        this.numberOfPlayers = inContract.getNumberOfPlayers();
        this.roleScores = new ArrayList<>();
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

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public short getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(short numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public List<RoleScoreVO> getRoleScores() {
        return roleScores;
    }

    public void setRoleScores(List<RoleScoreVO> roleScores) {
        this.roleScores = roleScores;
    }
}
