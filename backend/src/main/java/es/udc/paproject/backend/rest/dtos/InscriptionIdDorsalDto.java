package es.udc.paproject.backend.rest.dtos;

public class InscriptionIdDorsalDto {

    private Long id;
    private int dorsal;

    public InscriptionIdDorsalDto() {}

    public InscriptionIdDorsalDto(Long id, int dorsal) {
        this.id = id;
        this.dorsal = dorsal;
    }

    // GETTERS

    public Long getId() {
        return id;
    }

    public int getDorsal() {
        return dorsal;
    }

    // SETTERS

    public void setId(Long id) {
        this.id = id;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }
}
