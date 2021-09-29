package es.udc.paproject.backend.rest.dtos;

public class PickUpdDorsalDto {
    private Long inscriptionId;
    private Long sportEventId;
    private String creditCard;


    public PickUpdDorsalDto(Long inscriptionId, Long sportEventId, String creditCard) {
        this.inscriptionId = inscriptionId;
        this.sportEventId = sportEventId;
        this.creditCard = creditCard;
    }


    // GETTERS

    public Long getInscriptionId() {
        return inscriptionId;
    }

    public Long getSportEventId() {
        return sportEventId;
    }

    public String getCreditCard() {
        return creditCard;
    }


    // SETTERS

    public void setInscriptionId(Long inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public void setSportEventId(Long sportEventId) {
        this.sportEventId = sportEventId;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
}
