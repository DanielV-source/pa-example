package es.udc.paproject.backend.rest.dtos;

import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;


public class RateInscriptionDto {
    private int rateValue;
    public RateInscriptionDto() {}

    @NotNull
    @Range(min=1, max=5)
    public int getRateValue() { return rateValue; }

    public void setRateValue(int rateValue) { this.rateValue = rateValue; }
}
