package es.udc.paproject.backend.rest.dtos;


public class InscriptionDto {

    private Long inscriptionId;
    private Long sportEventId;
    private String sportEventName;
    private long date;
    private int dorsal;
    private String shortCreditCard;
    private boolean collected;
    private boolean rated;
    private int rateValue;
    private long sportEventDate;

    public InscriptionDto() {}

    public InscriptionDto(Long inscriptionId, Long sportEventId, String sportEventName, int dorsal,
                          String shortCreditCard, boolean collected, boolean rated, int rateValue,
                          long date, long sportEventDate) {
        this.inscriptionId = inscriptionId;
        this.sportEventId = sportEventId;
        this.sportEventName = sportEventName;
        this.dorsal = dorsal;
        this.shortCreditCard = shortCreditCard;
        this.collected = collected;
        this.rated = rated;
        this.rateValue = rateValue;
        this.date = date;
        this.sportEventDate = sportEventDate;
    }

    public InscriptionDto(Long inscriptionId, Long sportEventId, int dorsal, String shortCreditCard,
                          boolean collected, boolean rated, int rateValue, long date, long sportEventDate) {
        this.inscriptionId = inscriptionId;
        this.sportEventId = sportEventId;
        this.dorsal = dorsal;
        this.shortCreditCard = shortCreditCard;
        this.collected = collected;
        this.rated = rated;
        this.rateValue = rateValue;
        this.date = date;
        this.sportEventDate = sportEventDate;
    }

    // GETTERS

    public Long getInscriptionId() {
        return inscriptionId;
    }

    public Long getSportEventId() { return sportEventId; }

    public String getSportEventName() {
        return sportEventName;
    }

    public int getDorsal() {
        return dorsal;
    }

    public String getShortCreditCard() {
        return shortCreditCard;
    }

    public boolean isCollected() {
        return collected;
    }

    public boolean isRated() { return rated; }

    public int getRateValue() {
        return rateValue;
    }

    public long getDate() {
        return date;
    }

    public long getSportEventDate() { return sportEventDate; }

    // SETTERS

    public void setInscriptionId(Long inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public void setSportEventId(Long SportEventId) { this.sportEventId = sportEventId; }

    public void setSportEventName(String sportEventName) {
        this.sportEventName = sportEventName;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public void setShortCreditCard(String shortCreditCard) {
        this.shortCreditCard = shortCreditCard;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public void setRated(boolean rated) { this.rated = rated; }

    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setSportEventDate(long sportEventDate) { this.sportEventDate = sportEventDate; }
}
