package be.lionelh.whist.score.backend.data.domain;

import java.util.stream.Stream;

public enum EventStatus {
    NEW("Nouvelle"),
    IN_PROGRESS("En cours"),
    FINISHED("Terminée");

    private String value;

    private EventStatus(String inValue) {
        this.value = inValue;
    }

    public String getValue() {
        return this.value;
    }

    public static EventStatus findByValue(String inValue) {
        if (inValue == null) {
            return null;
        }

        return Stream.of(EventStatus.values())
                .filter(c -> c.getValue().equals(inValue))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
