package es.udc.paproject.backend.rest.dtos;

public class SportEventSummaryDto {

    private Long eventId;
    private String name;
    private long date;
    private Long province;
    private Long type;
    private int valueOfRates;
    private int numberOfRates;

    public SportEventSummaryDto(){}

    public SportEventSummaryDto(Long eventId, String name, Long type, Long province, long date,
                         int numberOfRates, int valueOfRates){
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.province = province;
        this.type = type;
        this.numberOfRates = numberOfRates;
        this.valueOfRates = valueOfRates;
    }


    // GETTERS

    public Long getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public long getDate() {
        return date;
    }

    public Long getProvince() {
        return province;
    }

    public Long getType() {
        return type;
    }

    public int getValueOfRates() {
        return valueOfRates;
    }

    public int getNumberOfRates() { return numberOfRates; }

    // SETTERS


    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setProvince(Long province) {
        this.province = province;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public void setValueOfRates(int valueOfRates) {
        this.valueOfRates = valueOfRates;
    }

    public void setNumberOfRates(int numberOfRates) { this.numberOfRates = numberOfRates; }

}
