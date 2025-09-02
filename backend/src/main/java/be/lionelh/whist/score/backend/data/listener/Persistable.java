package be.lionelh.whist.score.backend.data.listener;

import java.time.LocalDateTime;

public interface Persistable {

    void setCreationDate(LocalDateTime inDate);

    void setLastUpdateDate(LocalDateTime inDate);
}
