package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Province;
import es.udc.paproject.backend.model.entities.SportEvent;
import es.udc.paproject.backend.model.entities.SportEventType;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface CatalogService {
    List<Province> findAllProvinces();
    List<SportEventType> findAllSportEventTypes();
    Block<SportEvent> findSportEvent(Long provinceId, Long sportEventTypeId,
                                     LocalDate dateStart, LocalDate dateEnd, int page, int number);
    SportEvent getSportEvent(Long sportEventId)
            throws InstanceNotFoundException;
}
