package be.lionelh.whist.score.backend.data.converter;

import be.lionelh.whist.score.backend.data.domain.EventStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class EventStatusConverter implements AttributeConverter<EventStatus, String> {
    @Override
    public String convertToDatabaseColumn(EventStatus attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getValue();
    }

    @Override
    public EventStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(EventStatus.values()).filter(c -> c.getValue().equals(dbData)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
