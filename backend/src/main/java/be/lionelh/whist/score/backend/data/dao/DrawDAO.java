package be.lionelh.whist.score.backend.data.dao;

import be.lionelh.whist.score.backend.data.domain.Draw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrawDAO extends JpaRepository<Draw, Long> {

    List<Draw> findDrawsByEvent_Id(Long inEventId);
}
