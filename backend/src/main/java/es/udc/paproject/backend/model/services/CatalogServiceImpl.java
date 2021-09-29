package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    private SportEventDao sportEventDao;

    @Autowired
    private SportEventTypeDao sportEventTypeDao;

    @Autowired
    private ProvinceDao provinceDao;

    @Override
    public List<Province> findAllProvinces() {
        Iterable<Province> provinces = provinceDao.findAll(Sort.by(Sort.Direction.ASC, "name"));
        List<Province> provinceList = new ArrayList<>();

        provinces.forEach(provinceList::add);

        return provinceList;
    }

    @Override
    public List<SportEventType> findAllSportEventTypes() {
        Iterable<SportEventType> types = sportEventTypeDao.findAll(Sort.by(Sort.Direction.ASC, "name"));
        List<SportEventType> typeList = new ArrayList<>();

        types.forEach(typeList::add);

        return typeList;
    }

    @Override
    public Block<SportEvent> findSportEvent(Long provinceId, Long sportEventTypeId, LocalDate dateStart,
                                            LocalDate dateEnd, int page, int number) {

        Slice<SportEvent> slice = sportEventDao.findSportEvents(provinceId, sportEventTypeId, dateStart,
                dateEnd, PageRequest.of(page, number));
        return new Block<>(slice.getContent(), slice.hasNext());
    }

    @Override
    public SportEvent getSportEvent(Long sportEventId) throws InstanceNotFoundException {
        Optional<SportEvent> sportEvent = sportEventDao.findById(sportEventId);

        if(sportEvent.isEmpty()) {
            throw new InstanceNotFoundException("project.entities.sportEvent", sportEventId);
        }
        return sportEvent.get();
    }
}
