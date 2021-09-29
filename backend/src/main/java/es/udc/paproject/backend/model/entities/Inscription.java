package es.udc.paproject.backend.model.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Inscription {
    private Long inscriptionId;
    private User user;
    private SportEvent event;
    private int dorsal;
    private String creditCard;
    private boolean collected;
    private boolean rated;
    private int rateValue;
    private LocalDateTime date;

    public Inscription(){}

    public Inscription(User user, SportEvent event, int dorsal, String creditCard, boolean collected, boolean rated, int rateValue, LocalDateTime date) {
        this.user = user;
        this.event = event;
        this.dorsal = dorsal;
        this.creditCard = creditCard;
        this.collected = collected;
        this.rated = rated;
        this.rateValue = rateValue;
        this.date = date;
    }

    // GETTERS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getInscriptionId() {
        return inscriptionId;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    public User getUser() {
        return user;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="sportEventId")
    public SportEvent getEvent() {
        return event;
    }

    public int getDorsal() {
        return dorsal;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public boolean isCollected() {
        return collected;
    }

    public boolean isRated() {
        return rated;
    }

    public int getRateValue() {
        return rateValue;
    }

    public LocalDateTime getDate(){
        return date;
    }

    // SETTERS


    public void setInscriptionId(Long inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEvent(SportEvent event) {
        this.event = event;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }

    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
    }

    public void setDate(LocalDateTime date) { this.date = date;}
}
