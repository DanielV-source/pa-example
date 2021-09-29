package es.udc.paproject.backend.rest.dtos;

public class SportEventTypeDto {

    private Long typeId;
    private String name;

    public SportEventTypeDto(){}

    public SportEventTypeDto(Long typeId, String name) {
        this.typeId = typeId;
        this.name = name;
    }

    public Long getTypeId() {
        return typeId;
    }

    public String getName() {
        return name;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public void setName(String name) {
        this.name = name;
    }

}
