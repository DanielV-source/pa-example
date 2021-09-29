package es.udc.paproject.backend.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import es.udc.paproject.backend.model.entities.SportEventType;

public class SportEventTypeConversor {

    SportEventTypeConversor() {}

    public final static SportEventTypeDto toSportEventTypeDto(SportEventType sportEventType) {
        return new SportEventTypeDto(sportEventType.getTypeId(), sportEventType.getName());
    }

    public final static List<SportEventTypeDto> toSportEventTypeDtos(List<SportEventType> categories) {
        return categories.stream().map(c -> toSportEventTypeDto(c)).collect(Collectors.toList());
    }

}
