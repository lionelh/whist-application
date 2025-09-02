package be.lionelh.whist.score.backend.data.dao;

import be.lionelh.whist.score.backend.data.domain.ResultRole;
import be.lionelh.whist.score.backend.data.domain.pk.ResultRolePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRoleDAO extends JpaRepository<ResultRole, ResultRolePK> {

    List<ResultRole> findByResultId(Long inResultId);
}
