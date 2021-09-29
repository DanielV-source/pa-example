package es.udc.paproject.backend.rest.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EnrollParamsDto {

    private String creditCardNumber;
    private Long sportEventId;

    @NotNull
    @Size(min=16, max=16)
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    @NotNull
    public Long getSportEventId() { return sportEventId; }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
    public void setSportEventId(Long sportEventId) { this.sportEventId = sportEventId; }
}
