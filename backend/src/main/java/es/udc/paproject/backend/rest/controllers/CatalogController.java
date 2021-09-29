package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.SportEvent;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CatalogService;
import es.udc.paproject.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static es.udc.paproject.backend.rest.dtos.ProvinceConversor.toProvinceDtos;
import static es.udc.paproject.backend.rest.dtos.SportEventConversor.*;
import static es.udc.paproject.backend.rest.dtos.SportEventTypeConversor.toSportEventTypeDtos;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping("/types")
    public List<SportEventTypeDto> findAllSportEventTypes() {
        return toSportEventTypeDtos(catalogService.findAllSportEventTypes());
    }

    @GetMapping("/provinces")
    public List<ProvinceDto> findAllProvinces() {
        return toProvinceDtos(catalogService.findAllProvinces());
    }

    @GetMapping("/sportevents/{sportEventId}")
    public SportEventDto findSportEventById(@PathVariable Long sportEventId) throws InstanceNotFoundException {
        return toSportEventDto(catalogService.getSportEvent(sportEventId));
    }

    @GetMapping("/sportevents")
    public BlockDto<SportEventSummaryDto> findSportEvent(
            @RequestParam(required = false) Long provinceId,
            @RequestParam(required = false) Long sportEventTypeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd,
            @RequestParam(defaultValue = "0") int page) {


        Block<SportEvent> sportEventBlock = catalogService.findSportEvent(provinceId, sportEventTypeId,
                dateStart != null ? (LocalDateTime.of(dateStart, LocalTime.now())).toLocalDate() : null,
                dateEnd != null ? (LocalDateTime.of(dateEnd, LocalTime.now())).toLocalDate() : null, page, 2);

        return new BlockDto<>(toSportEventSummaryDtos(sportEventBlock.getItems()), sportEventBlock.getExistMoreItems());
    }
}
