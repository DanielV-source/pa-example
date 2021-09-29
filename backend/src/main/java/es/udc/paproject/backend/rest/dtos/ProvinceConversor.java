package es.udc.paproject.backend.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import es.udc.paproject.backend.model.entities.Province;

public class ProvinceConversor {

    ProvinceConversor() {}

    public final static ProvinceDto toProvinceDto(Province province) {
        return new ProvinceDto(province.getProvinceId(), province.getName());
    }

    public final static List<ProvinceDto> toProvinceDtos(List<Province> provinces) {
        return provinces.stream().map(c -> toProvinceDto(c)).collect(Collectors.toList());
    }
}
