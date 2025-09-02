package be.lionelh.whist.score.backend.data.dao;

import be.lionelh.whist.score.backend.data.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDAO extends JpaRepository<Event, Long> {
}
