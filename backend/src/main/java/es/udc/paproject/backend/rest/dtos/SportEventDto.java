package es.udc.paproject.backend.rest.dtos;

public class SportEventDto {
    private Long eventId;
    private String name;
    private String description;
    private long date;
    private Float price;
    private int maxParticipants;
    private int numParticipants;
    private String location;
    private Long province;
    private Long type;
    private int numberOfRates;
    private int valueOfRates;

    public SportEventDto(){}

    public SportEventDto(Long eventId, String name, String description, long date,
                         Float price, int maxParticipants, int numParticipants, String location,
                         Long province, Long type, int numberOfRates, int valueOfRates) {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.date = date;
        this.price = price;
        this.maxParticipants = maxParticipants;
        this.numParticipants = numParticipants;
        this.location = location;
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

    public String getDescription() {
        return description;
    }

    public long getDate() {
        return date;
    }

    public Float getPrice() {
        return price;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public int getNumParticipants() {
        return numParticipants;
    }

    public String getLocation() {
        return location;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public void setNumParticipants(int numParticipants){
        this.numParticipants = numParticipants;
    }

    public void setLocation(String location) {
        this.location = location;
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
