package es.udc.paproject.backend.test.model.services;

import static org.junit.jupiter.api.Assertions.*;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CatalogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CatalogServiceTest {


    @Autowired
    private SportEventDao sportEventDao;

    @Autowired
    private SportEventTypeDao sportEventTypeDao;

    @Autowired
    private ProvinceDao provinceDao;

    @Autowired
    private CatalogService catalogService;


    @Test
    public void testFindAllTypes() {
        SportEventType type1 = new SportEventType("Natación");
        SportEventType type2 = new SportEventType("Running");

        sportEventTypeDao.save(type1);
        sportEventTypeDao.save(type2);

        assertEquals(Arrays.asList(type1, type2), catalogService.findAllSportEventTypes());
    }

    @Test
    public void testFindAllProvinces() {
        Province province1 = new Province("A Coruña");
        Province province2 = new Province("Lugo");

        provinceDao.save(province1);
        provinceDao.save(province2);

        assertEquals(Arrays.asList(province1, province2), catalogService.findAllProvinces());
    }

    @Test
    public void testFindSportEventByNotExistenId(){
        assertThrows(InstanceNotFoundException.class, () -> catalogService.getSportEvent((long) 0));
    }

    @Test
    public void testFindSportEventById() throws InstanceNotFoundException{
        SportEventType type1 = new SportEventType("Running");
        Province province1 = new Province("A Coruña");
        LocalDateTime date = LocalDateTime.now().plusYears(1);

        SportEvent event = new SportEvent("Running Oleiros", "Carrera en la localidad de Oleiros",
                date, (float) 10, 100, "Oleiros", province1, type1);

        sportEventTypeDao.save(type1);
        provinceDao.save(province1);
        sportEventDao.save(event);

        assertEquals(event, catalogService.getSportEvent(event.getEventId()));
    }


    @Test
    public void testFindSportEventByProvince() {
        Province province1 = new Province("A Coruña");
        Province province2 = new Province("Lugo");
        SportEventType type1 = new SportEventType("Running");
        LocalDateTime date = LocalDateTime.now().plusYears(1);

        SportEvent event1 = new SportEvent("Running Oleiros", "Carrera en la localidad de Oleiros",
                date, (float) 10, 100, "Oleiros", province1, type1);

        SportEvent event2 = new SportEvent("Running Lugo", "Carrera en la ciudad de Lugo",
                date, (float) 10, 100, "Oleiros", province2, type1);

        provinceDao.save(province1);
        provinceDao.save(province2);
        sportEventTypeDao.save(type1);
        sportEventDao.save(event1);
        sportEventDao.save(event2);

        Block<SportEvent> expectedEvent = new Block<>(Arrays.asList(event1), false);

        assertEquals(expectedEvent, catalogService.findSportEvent(province1.getProvinceId(),
                null, null, null, 0, 1));
    }

    @Test
    public void testFindSportEventByType() {
        Province province1 = new Province("A Coruña");
        SportEventType type1 = new SportEventType("Running");
        SportEventType type2 = new SportEventType("Natación");
        LocalDateTime date = LocalDateTime.now().plusYears(1);

        SportEvent event1 = new SportEvent("Running Oleiros", "Carrera en la localidad de Oleiros",
                date, (float) 10, 100, "Oleiros", province1, type1);

        SportEvent event2 = new SportEvent("Natación Oleiros", "50m libres en la localidad de Oleiros",
                date, (float) 10, 100, "Oleiros", province1, type2);

        provinceDao.save(province1);
        sportEventTypeDao.save(type1);
        sportEventTypeDao.save(type2);
        sportEventDao.save(event1);
        sportEventDao.save(event2);

        Block<SportEvent> expectedEvent = new Block<>(Arrays.asList(event1), false);

        assertEquals(expectedEvent, catalogService.findSportEvent(null,
                type1.getTypeId(), null, null, 0, 1));
    }

    @Test
    public void testFindSportEventByDate() {
        Province province1 = new Province("A Coruña");
        SportEventType type1 = new SportEventType("Running");
        LocalDateTime dateStart = LocalDateTime.now().plusMonths(7);
        LocalDateTime dateEnd = LocalDateTime.now().plusYears(2);

        LocalDateTime date1 = LocalDateTime.now().plusMonths(10);
        LocalDateTime date2 = LocalDateTime.now().plusMonths(15);

        SportEvent event1 = new SportEvent("Running Coruña", "Carrera en el centro de la ciudad",
                date1, (float) 10, 100, "Oleiros", province1, type1);

        SportEvent event2 = new SportEvent("Running Oleiros", "Carrera en la localidad de Oleiros",
                date2, (float) 10, 100, "Oleiros", province1, type1);

        provinceDao.save(province1);
        sportEventTypeDao.save(type1);
        sportEventDao.save(event1);
        sportEventDao.save(event2);

        Block<SportEvent> expectedEvent = new Block<>(Arrays.asList(event2, event1), false);

        assertEquals(expectedEvent, catalogService.findSportEvent(null,
                null, dateStart.toLocalDate(), dateEnd.toLocalDate(), 0, 2));
    }

    @Test
    public void testFindSportEventByAllCriteria() {
        Province province1 = new Province("A Coruña");
        Province province2 = new Province("Lugo");
        SportEventType type1 = new SportEventType("Running");
        SportEventType type2 = new SportEventType("Natación");
        LocalDateTime dateStart = LocalDateTime.now().plusMonths(7);
        LocalDateTime dateEnd = LocalDateTime.now().plusYears(2);

        LocalDateTime date1 = LocalDateTime.now().plusMonths(10);
        LocalDateTime date2 = LocalDateTime.now().plusMonths(15);

        SportEvent event1 = new SportEvent("Running Oleiros", "Carrera en la localidad de Oleiros",
                date1, (float) 10, 100, "Oleiros", province1, type1);

        SportEvent event2 = new SportEvent("Natación en Lugo", "50m libre en la ciudad de Lugo",
                date2, (float) 10, 100, "Lugo ciudad", province2, type2);

        provinceDao.save(province1);
        provinceDao.save(province2);
        sportEventTypeDao.save(type1);
        sportEventTypeDao.save(type2);
        sportEventDao.save(event1);
        sportEventDao.save(event2);

        Block<SportEvent> expectedEvent = new Block<>(Arrays.asList(event1), false);
        Block<SportEvent> expectedEvent2 = new Block<>(new ArrayList<>(), false);

        assertEquals(expectedEvent, catalogService.findSportEvent(province1.getProvinceId(),
                type1.getTypeId(), dateStart.toLocalDate(), dateEnd.toLocalDate(), 0, 1));

        assertEquals(expectedEvent2, catalogService.findSportEvent(province1.getProvinceId(),
                type2.getTypeId(), dateStart.toLocalDate(), dateEnd.toLocalDate(), 0, 1));
    }

    @Test
    public void testFindAllEvents() {
        Province province1 = new Province("A Coruña");
        Province province2 = new Province("Lugo");
        SportEventType type1 = new SportEventType("Running");
        SportEventType type2 = new SportEventType("Natación");

        LocalDateTime date1 = LocalDateTime.now().plusMonths(10);
        LocalDateTime date2 = LocalDateTime.now().plusMonths(15);

        SportEvent event1 = new SportEvent("Running Oleiros", "Carrera en la localidad de Oleiros",
                date1, (float) 10, 100, "Oleiros", province1, type1);

        SportEvent event2 = new SportEvent("Natación en Lugo", "50m libre en la ciudad de Lugo",
                date2, (float) 10, 100, "Lugo ciudad", province2, type2);

        provinceDao.save(province1);
        provinceDao.save(province2);
        sportEventTypeDao.save(type1);
        sportEventTypeDao.save(type2);
        sportEventDao.save(event1);
        sportEventDao.save(event2);

        Block<SportEvent> expectedEvent = new Block<>(Arrays.asList(event2, event1), false);

        assertEquals(expectedEvent, catalogService.findSportEvent(null, null,
                null, null, 0, 2));
    }

    @Test
    public void testFindNoEvents() {
        Province province1 = new Province("A Coruña");
        SportEventType type1 = new SportEventType("Running");

        LocalDateTime date1 = LocalDateTime.now().plusMonths(10);

        SportEvent event1 = new SportEvent("Running Oleiros", "Carrera en la localidad de Oleiros",
                date1, (float) 10, 100, "Oleiros", province1, type1);


        provinceDao.save(province1);
        sportEventTypeDao.save(type1);
        sportEventDao.save(event1);

        Block<SportEvent> expectedEvent = new Block<>(new ArrayList<>(), false);

        assertEquals(expectedEvent, catalogService.findSportEvent((long) 50, null,
                null, null, 0, 1));
    }

    @Test
    public void testFindEventsByPages() {
        Province province1 = new Province("A Coruña");
        Province province2 = new Province("Lugo");
        SportEventType type1 = new SportEventType("Running");
        SportEventType type2 = new SportEventType("Natación");

        LocalDateTime date1 = LocalDateTime.now().plusMonths(10);
        LocalDateTime date2 = LocalDateTime.now().plusMonths(15);

        SportEvent event1 = new SportEvent("Running en Oleiros", "Carrera en la localidad de Oleiros",
                date1, (float) 10, 100, "Oleiros", province1, type1);

        SportEvent event2 = new SportEvent("Natación en Lugo", "50m libre en la ciudad de Lugo",
                date2, (float) 10, 100, "Lugo ciudad", province2, type2);

        SportEvent event3 = new SportEvent("Running en Lugo", "Carrera en la ciudad de Lugo",
                date2, (float) 10, 100, "Lugo ciudad", province2, type1);

        provinceDao.save(province1);
        provinceDao.save(province2);
        sportEventTypeDao.save(type1);
        sportEventTypeDao.save(type2);
        sportEventDao.save(event1);
        sportEventDao.save(event2);
        sportEventDao.save(event3);

        Block<SportEvent> expectedEvent = new Block<>(Arrays.asList(event2, event3), true);
        assertEquals(expectedEvent, catalogService.findSportEvent(null,
                null, null, null, 0, 2));


        expectedEvent = new Block<>(Arrays.asList(event1), false);
        assertEquals(expectedEvent, catalogService.findSportEvent(null,
                null, null, null, 1, 2));


        expectedEvent = new Block<>(new ArrayList<>(), false);
        assertEquals(expectedEvent, catalogService.findSportEvent(null,
                null, null, null, 2, 2));
    }

}
