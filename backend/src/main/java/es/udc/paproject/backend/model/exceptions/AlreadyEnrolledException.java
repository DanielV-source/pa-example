package es.udc.paproject.backend.model.exceptions;

public class AlreadyEnrolledException extends Exception {

    private Long inscriptionId;

    public AlreadyEnrolledException(Long inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public Long getAlreadyEnrolled() {
        return inscriptionId;
    }
}
