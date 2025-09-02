package be.lionelh.whist.score.backend.data.dao;

import be.lionelh.whist.score.backend.data.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerDAO extends JpaRepository<Player, Long> {

    boolean existsPlayerByName(String name);

    Optional<Player> findPlayerByNameEqualsIgnoreCase(String inName);
}
