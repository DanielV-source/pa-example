package es.udc.paproject.backend.rest.dtos;

public class ProvinceDto {

    private Long provinceId;
    private String name;

    public ProvinceDto() {}

    public ProvinceDto(Long provinceId, String name) {
        this.provinceId = provinceId;
        this.name = name;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public String getName() {
        return name;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
