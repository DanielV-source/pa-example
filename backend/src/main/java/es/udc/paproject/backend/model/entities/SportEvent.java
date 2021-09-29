package es.udc.paproject.backend.model.entities;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SportEvent {
    private Long eventId;
    private String name;
    private String description;
    private LocalDateTime date;
    private Float price;
    private int maxParticipants;
    private int numParticipants;
    private String location;
    private Province province;
    private SportEventType type;
    private int numberOfRates;
    private int valueOfRates;
    private long version;

    public SportEvent(){}
    public SportEvent(Long eventId, String name, String description, LocalDateTime date,
                      Float price, int maxParticipants, int numParticipants, String location, Province province,
                      SportEventType type, int numberOfRates, int valueOfRates) {
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

    public SportEvent(String name, String description, LocalDateTime date, Float price,
                      int maxParticipants, String location, Province province, SportEventType type) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.price = price;
        this.maxParticipants = maxParticipants;
        this.location = location;
        this.province = province;
        this.type = type;
        this.numberOfRates = 0;
        this.valueOfRates = 0;
        this.numParticipants = 0;
    }

    // GETTERS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="provinceId")
    public Province getProvince() {
        return province;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="typeId")
    public SportEventType getType() {
        return type;
    }

    public int getNumberOfRates() {
        return numberOfRates;
    }
    public int getValueOfRates() {
        return valueOfRates;
    }

    @Version
    public long getVersion() {
        return version;
    }
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

    public void setDate(LocalDateTime date) {
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

    public void setProvince(Province province) {
        this.province = province;
    }

    public void setType(SportEventType type) {
        this.type = type;
    }

    public void setNumberOfRates(int numberOfRates) {
        this.numberOfRates = numberOfRates;
    }

    public void setValueOfRates(int valueOfRates) {
        this.valueOfRates = valueOfRates;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Transient
    public boolean isFull() {
        return (numParticipants+1 > maxParticipants);
    }
}
