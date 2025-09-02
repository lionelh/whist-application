package be.lionelh.whist.score.backend.data.dao;

import be.lionelh.whist.score.backend.data.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractDAO extends JpaRepository<Contract, Long> {

    boolean existsContractByNameAndNumberOfPlayers(String inName, short inNumberOfPlayers);

    List<Contract> findContractsByNumberOfPlayers(int inNumberOfPlayers);

}
