package be.lionelh.whist.score.backend.data.dao;

import be.lionelh.whist.score.backend.data.domain.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultDAO extends JpaRepository<Result, Long> {

    List<Result> findResultsByContract_Id(Long inContractId);
}
