package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.SportEvent;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class SportEventConversor {
    public SportEventConversor() {}

    public final static SportEventDto toSportEventDto(SportEvent sportEvent) {
        return new SportEventDto(sportEvent.getEventId(), sportEvent.getName(), sportEvent.getDescription(),
                toMillis(sportEvent.getDate()), sportEvent.getPrice(), sportEvent.getMaxParticipants(),
                sportEvent.getNumParticipants(), sportEvent.getLocation(), sportEvent.getProvince().getProvinceId(),
                 sportEvent.getType().getTypeId(), sportEvent.getNumberOfRates(), sportEvent.getValueOfRates());
    }

    public final static List<SportEventSummaryDto> toSportEventSummaryDtos(List<SportEvent> events) {
        return events.stream().map(e -> toSportEventSummaryDto(e)).collect(Collectors.toList());
    }

    public final static SportEventSummaryDto toSportEventSummaryDto(SportEvent event) {
        return new SportEventSummaryDto(event.getEventId(), event.getName(), event.getType().getTypeId(), event.getProvince().getProvinceId(),
                toMillis(event.getDate()), event.getNumberOfRates(), event.getValueOfRates());
    }

    private final static long toMillis(LocalDateTime date) {
        return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    }
}
