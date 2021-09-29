package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Inscription;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class InscriptionConversor {

    private InscriptionConversor() {}

    public final static List<InscriptionDto> toInscriptionDtos(List<Inscription> inscriptions) {
        return inscriptions.stream().map(o -> toInscriptionDto(o)).collect(Collectors.toList());
    }

    public final static InscriptionDto toInscriptionDto(Inscription inscription) {

        String shortCard = inscription.getCreditCard().substring(12);

        return new InscriptionDto(inscription.getInscriptionId(), inscription.getEvent().getEventId(),
                inscription.getEvent().getName(), inscription.getDorsal(), shortCard, inscription.isCollected(),
                inscription.isRated(), inscription.getRateValue(), toMillis(inscription.getDate()),
                toMillis(inscription.getEvent().getDate()));
    }

    public final static InscriptionIdDorsalDto toInscriptionIdDorsalDto(Inscription inscription) {
        return new InscriptionIdDorsalDto(inscription.getInscriptionId(), inscription.getDorsal());
    }

    private final static long toMillis(LocalDateTime date) {
        return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    }
}
