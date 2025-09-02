package be.lionelh.whist.score.backend.data.dao;

import be.lionelh.whist.score.backend.data.domain.Contract;
import be.lionelh.whist.score.backend.data.domain.PlayerDraw;
import be.lionelh.whist.score.backend.data.domain.pk.PlayerDrawPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerDrawDAO extends JpaRepository<PlayerDraw, PlayerDrawPK> {

}
