package es.udc.paproject.backend.test.model.services;

import static org.junit.jupiter.api.Assertions.*;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.Block;

import es.udc.paproject.backend.model.services.InscriptionService;
import es.udc.paproject.backend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class InscriptionServiceTest {
    private final Long NON_EXISTENT_ID = Long.valueOf(-1);
    private final String VALID_CREDIT_CARD = "1234567890123456";
    private final String INVALID_CREDIT_CARD = "1111111111111111";

    @Autowired
    private UserService userService;

    @Autowired
    private SportEventDao sportEventDao;

    @Autowired
    private ProvinceDao provinceDao;

    @Autowired
    private SportEventTypeDao sportEventTypeDao;

    @Autowired
    private InscriptionDao inscriptionDao;

    @Autowired
    private InscriptionService inscriptionService;


    private Inscription addInscription(User user, SportEvent sportEvent, String cCard, LocalDateTime date) {

        sportEvent.setNumParticipants(sportEvent.getNumParticipants()+1);
        int num_dorsal = sportEvent.getNumParticipants();

        Inscription inscription = new Inscription(user, sportEvent, num_dorsal, cCard, false,
                false, 0, date);

        inscriptionDao.save(inscription);

        return inscription;
    }

    private User signUpUser(String userName) {

        User user = new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com");

        try {
            userService.signUp(user);
        } catch (DuplicateInstanceException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    private SportEvent createSportEvent(String name, String description, LocalDateTime date, Float price,
                                        int maxParticipants, String location, String province,
                                        String type) {
        SportEventType type1 = new SportEventType(type);
        Province province1 = new Province(province);

        SportEvent event = new SportEvent(name, description, date, price, maxParticipants,
                location, province1, type1);

        sportEventTypeDao.save(type1);
        provinceDao.save(province1);
        sportEventDao.save(event);

        return event;
    }

    private SportEvent createRandomSportEvent(){
        SportEventType type1 = new SportEventType("Running");
        Province province1 = new Province("A Coruña");
        LocalDateTime date = LocalDateTime.now().plusHours(48);

        SportEvent event = new SportEvent("Running Oleiros", "Carrera en la localidad de Oleiros",
                date, (float) 10, 100, "Oleiros", province1, type1);

        sportEventTypeDao.save(type1);
        provinceDao.save(province1);
        sportEventDao.save(event);

        return event;
    }
    private SportEvent generateRandomSportEvent(){
        SportEventType type1 = new SportEventType("Running");
        Province province1 = new Province("A Coruña");
        int maxDays = 15*24;
        int minDays = 0;
        LocalDateTime date = LocalDateTime.now().minusHours(new Random().nextInt(maxDays - minDays) + minDays);

        SportEvent event = new SportEvent("Running Oleiros", "Carrera en la localidad de Oleiros",
                date, (float) 10, 100, "Oleiros", province1, type1);

        sportEventTypeDao.save(type1);
        provinceDao.save(province1);
        sportEventDao.save(event);

        return event;
    }
    private SportEvent expiredRandomSportEvent(boolean after){
        SportEventType type1 = new SportEventType("Running");
        Province province1 = new Province("A Coruña");
        LocalDateTime date;
        if(after) date = LocalDateTime.now().plusDays(16);
        else date = LocalDateTime.now().minusDays(16);
        SportEvent event = new SportEvent("Running Oleiros", "Carrera en la localidad de Oleiros",
                date, (float) 10, 100, "Oleiros", province1, type1);

        sportEventTypeDao.save(type1);
        provinceDao.save(province1);
        sportEventDao.save(event);

        return event;
    }

    @Test
    public void testEnrollSportEvent() throws ExpiredEnrollException, MaxParticipantsException,
            InstanceNotFoundException, AlreadyEnrolledException {

        User user = signUpUser("user");
        SportEvent event1 = createSportEvent("Running", "Carrera en la localidad de Oleiros",
                LocalDateTime.now().plusYears(1), (float) 10, 2, "Oleiros",
                "A Coruña", "Running");

        String creditCard = "2720994985726875";

        assertEquals(0, event1.getNumParticipants());

        Inscription inscription = inscriptionService.enrollSportEvent(user.getId(), creditCard,
                event1.getEventId());

        Inscription inscription1 = inscriptionDao.findByUserIdAndEventEventId(user.getId(), event1.getEventId()).get();

        assertEquals(inscription, inscription1);
        assertEquals(user, inscription.getUser());
        assertEquals(event1, inscription.getEvent());
        assertEquals(creditCard, inscription.getCreditCard());
        assertEquals(1,inscription.getEvent().getNumParticipants());

        User user2 = signUpUser("a");
        Inscription inscription2 = inscriptionService.enrollSportEvent(user2.getId(), creditCard,
                event1.getEventId());

        assertEquals(2, inscription2.getEvent().getNumParticipants());
        assertEquals(inscription.getEvent().getNumParticipants(), inscription2.getEvent().getNumParticipants());
        assertTrue(event1.isFull());
    }

    @Test
    public void testAlreadyEnrolledSportEvent() throws MaxParticipantsException, AlreadyEnrolledException,
            InstanceNotFoundException, ExpiredEnrollException {

        User user = signUpUser("user");
        SportEvent event1 = createSportEvent("Running", "Carrera en la localidad de Oleiros",
                LocalDateTime.now().plusYears(1), (float) 10, 2, "Oleiros",
                "A Coruña", "Running");

        String creditCard = "2720994985726875";
        Inscription inscription = inscriptionService.enrollSportEvent(user.getId(), creditCard,
                event1.getEventId());

        assertThrows(AlreadyEnrolledException.class, () ->
                inscriptionService.enrollSportEvent(user.getId(), creditCard,
                        event1.getEventId()));
    }

    @Test
    public void testFullParticipantsSportEvent() throws MaxParticipantsException, AlreadyEnrolledException,
            InstanceNotFoundException, ExpiredEnrollException {

        User user1 = signUpUser("user1");
        User user2 = signUpUser("user2");
        User user3 = signUpUser("user3");

        SportEvent event1 = createSportEvent("Running", "Carrera en la localidad de Oleiros",
                LocalDateTime.now().plusYears(1), (float) 10, 2, "Oleiros",
                "A Coruña", "Running");

        String creditCard = "2720994985726875";
        inscriptionService.enrollSportEvent(user1.getId(), creditCard,
                event1.getEventId());
        inscriptionService.enrollSportEvent(user2.getId(), creditCard,
                event1.getEventId());

        assertThrows(MaxParticipantsException.class, () ->
                inscriptionService.enrollSportEvent(user3.getId(), creditCard,
                        event1.getEventId()));
    }

    @Test
    public void testAlreadyExpiredSportEvent() throws MaxParticipantsException, AlreadyEnrolledException,
            InstanceNotFoundException, ExpiredEnrollException {

        User user = signUpUser("user");
        SportEvent event1 = createSportEvent("Running", "Carrera en la localidad de Oleiros",
                LocalDateTime.now(), (float) 10, 2, "Oleiros",
                "A Coruña", "Running");

        String creditCard = "2720994985726875";

        assertThrows(ExpiredEnrollException.class, () ->
                inscriptionService.enrollSportEvent(user.getId(), creditCard,
                        event1.getEventId()));
    }

    @Test
    public void testUserIdNotFoundSportEvent() throws MaxParticipantsException, AlreadyEnrolledException,
            InstanceNotFoundException, ExpiredEnrollException {

        SportEvent event1 = createSportEvent("Running", "Carrera en la localidad de Oleiros",
                LocalDateTime.now().plusYears(1), (float) 10, 2, "Oleiros",
                "A Coruña", "Running");

        String creditCard = "2720994985726875";

        assertThrows(InstanceNotFoundException.class, () ->
                inscriptionService.enrollSportEvent(NON_EXISTENT_ID, creditCard,
                        event1.getEventId()));
    }

    @Test
    public void testEventIdNotFoundSportEvent() throws MaxParticipantsException, AlreadyEnrolledException,
            InstanceNotFoundException, ExpiredEnrollException {

        User user = signUpUser("user");

        String creditCard = "2720994985726875";

        assertThrows(InstanceNotFoundException.class, () ->
                inscriptionService.enrollSportEvent(user.getId(), creditCard,
                        NON_EXISTENT_ID));
    }


    @Test
    public void testPickUpDorsal() throws MaxParticipantsException, AlreadyEnrolledException,
            InstanceNotFoundException, ExpiredEnrollException, CreditCardNumberException, ExpiredPickUpException,
            DorsalAlreadyTakenException, InscriptionCodeException {

        SportEvent event = createRandomSportEvent();
        User user = signUpUser("Pablo");

        Inscription inscription = inscriptionService.enrollSportEvent(user.getId(),
                VALID_CREDIT_CARD, event.getEventId());

        event.setDate(LocalDateTime.now().plusHours(8));
        sportEventDao.save(event);

        assertFalse(inscription.isCollected());

        inscriptionService.pickUpDorsal(inscription.getInscriptionId(), event.getEventId(), VALID_CREDIT_CARD);

        assertTrue(inscription.isCollected());
    }

    @Test
    public void testExpiredDatePickUpDorsal(){
        // Expired Pick up date
        assertThrows(ExpiredPickUpException.class, () -> {
            SportEvent event = createRandomSportEvent();
            User user = signUpUser("Ejemplo1");

            Inscription inscription = inscriptionService.enrollSportEvent(user.getId(),
                    VALID_CREDIT_CARD, event.getEventId());
            inscriptionService.pickUpDorsal(inscription.getInscriptionId(), event.getEventId(), VALID_CREDIT_CARD);
        });
    }

    @Test
    public void testDorsalAlreadyTakenPickUpDorsal(){
        // Dorsal already taken
        assertThrows(DorsalAlreadyTakenException.class, () ->{
            SportEvent event = createRandomSportEvent();
            User user = signUpUser("Ejemplo2");

            Inscription inscription = inscriptionService.enrollSportEvent(user.getId(),
                    VALID_CREDIT_CARD, event.getEventId());

            event.setDate(LocalDateTime.now().plusHours(8));
            sportEventDao.save(event);

            inscriptionService.pickUpDorsal(inscription.getInscriptionId(), event.getEventId(), VALID_CREDIT_CARD);

            inscriptionService.pickUpDorsal(inscription.getInscriptionId(), event.getEventId(), VALID_CREDIT_CARD);
        });
    }

    @Test
    public void testWrongInscriptionIdPickUpDorsal(){
        // Inscription does not match sport event
        assertThrows(InscriptionCodeException.class, () -> {
            SportEvent event = createRandomSportEvent();
            SportEvent event2 = createRandomSportEvent();
            User user = signUpUser("Ejemplo3");

            Inscription inscription = inscriptionService.enrollSportEvent(user.getId(),
                    VALID_CREDIT_CARD, event.getEventId());

            Inscription inscription2 = inscriptionService.enrollSportEvent(user.getId(),
                    VALID_CREDIT_CARD, event2.getEventId());

            event.setDate(LocalDateTime.now().plusHours(8));
            sportEventDao.save(event);

            event2.setDate(LocalDateTime.now().plusHours(8));
            sportEventDao.save(event2);

            inscriptionService.pickUpDorsal(inscription.getInscriptionId(), event2.getEventId(), VALID_CREDIT_CARD);
        });
    }

    @Test
    public void testWrongCreditCardPickUpDorsal(){
        // Credit card does not match inscription
        assertThrows(CreditCardNumberException.class, () -> {
            SportEvent event = createRandomSportEvent();
            User user = signUpUser("Ejemplo4");

            Inscription inscription = inscriptionService.enrollSportEvent(user.getId(),
                    VALID_CREDIT_CARD, event.getEventId());

            event.setDate(LocalDateTime.now().plusHours(8));
            sportEventDao.save(event);

            inscriptionService.pickUpDorsal(inscription.getInscriptionId(), event.getEventId(), INVALID_CREDIT_CARD);
        });
    }
    @Test
    public void testInvalidSportEventIdPickUpDorsal(){
        assertThrows(InstanceNotFoundException.class, () ->{
            SportEvent event = createRandomSportEvent();
            User user = signUpUser("Ejemplo5");

            Inscription inscription = inscriptionService.enrollSportEvent(user.getId(),
                    VALID_CREDIT_CARD, event.getEventId());

            event.setDate(LocalDateTime.now().plusHours(8));
            sportEventDao.save(event);

            inscriptionService.pickUpDorsal(inscription.getInscriptionId(), (long)-1, VALID_CREDIT_CARD);
        });
    }



    @Test
    public void testInvalidInscriptionIdPickUpDorsal() {
        assertThrows(InstanceNotFoundException.class, () ->{
            SportEvent event = createRandomSportEvent();
            User user = signUpUser("Ejemplo6");

            Inscription inscription = inscriptionService.enrollSportEvent(user.getId(),
                    VALID_CREDIT_CARD, event.getEventId());

            event.setDate(LocalDateTime.now().plusHours(8));
            sportEventDao.save(event);

            inscriptionService.pickUpDorsal((long) -1, event.getEventId(), VALID_CREDIT_CARD);
        });
    }

    @Test
    public void testShowInscriptions() {

        User user = signUpUser("user");
        SportEvent event1 = createRandomSportEvent();
        SportEvent event2 = createRandomSportEvent();
        SportEvent event3 = createRandomSportEvent();
        String creditCard = "2720994985726874";

        Inscription inscription1 = addInscription(user, event1, VALID_CREDIT_CARD,
                LocalDateTime.of(2019, 1, 1, 10, 2, 3));
        Inscription inscription2 = addInscription(user, event2, creditCard,
                LocalDateTime.of(2020, 6, 1, 10, 2, 3));
        Inscription inscription3 = addInscription(user, event3, VALID_CREDIT_CARD,
                LocalDateTime.of(2020, 12, 1, 10, 2, 3));

        Block<Inscription> expectedBlock = new Block<>(Arrays.asList(inscription3, inscription2), true);
        Block<Inscription> listInscriptions = inscriptionService.showInscriptions(user.getId(), 0, 2);

        assertEquals(expectedBlock, listInscriptions);

        Block<Inscription> expectedBlock2 = new Block<>(Arrays.asList(inscription1), false); //last inscription
        Block<Inscription> listInscriptions2 = inscriptionService.showInscriptions(user.getId(), 1, 2);

        assertEquals(expectedBlock2, listInscriptions2);
    }

    @Test
    public void testShowNoInscriptions() {

        User user = signUpUser("user");
        Block<Inscription> expectedInscriptions = new Block<>(new ArrayList<>(), false);
        Block<Inscription> listInscriptions = inscriptionService.showInscriptions(user.getId(), 0, 1);

        assertEquals(expectedInscriptions, listInscriptions);
    }

    @Test
    public void testInstanceNotFoundRateSportEvent() throws InstanceNotFoundException {
        User user = signUpUser("user");
        assertThrows(InstanceNotFoundException.class, ()-> {
            inscriptionService.rateSportEvent(NON_EXISTENT_ID, user.getId(), 2);
        });
    }

    @Test
    public void testPermissionRateSportEvent() throws PermissionException {
        User user = signUpUser("user");
        SportEvent event1 = generateRandomSportEvent();
        Inscription inscription1 = addInscription(user, event1, VALID_CREDIT_CARD,
                LocalDateTime.of(2019, 1, 1, 10, 2, 3));
        assertThrows(PermissionException.class, ()-> {
            inscriptionService.rateSportEvent(inscription1.getInscriptionId(), NON_EXISTENT_ID, 4);
        });
    }

    @Test
    public void testExpiredDateRateSportEvent() throws ExpiredRateDateException {
        User user = signUpUser("user");
        SportEvent event2 = expiredRandomSportEvent(false); // Antes de 15 días
        SportEvent event3 = expiredRandomSportEvent(true);  // Fecha futura
        Inscription inscription1 = addInscription(user, event2, VALID_CREDIT_CARD,
                LocalDateTime.now());
        Inscription inscription2 = addInscription(user, event3, VALID_CREDIT_CARD,
                LocalDateTime.now());
        assertThrows(ExpiredRateDateException.class, ()-> {
            inscriptionService.rateSportEvent(inscription1.getInscriptionId(), user.getId(), 2);
        });
        assertThrows(ExpiredRateDateException.class, ()-> {
            inscriptionService.rateSportEvent(inscription2.getInscriptionId(), user.getId(), 2);
        });
    }

    @Test
    public void testAlreadyRatedRateSportEvent() throws PermissionException, InstanceNotFoundException,
            ExpiredRateDateException, AlreadyRatedException {
        User user = signUpUser("user");
        SportEvent event1 = generateRandomSportEvent();
        Inscription inscription1 = addInscription(user, event1, VALID_CREDIT_CARD,
                LocalDateTime.of(2019, 1, 1, 10, 2, 3));
        inscriptionService.rateSportEvent(inscription1.getInscriptionId(), user.getId(), 4);
        assertThrows(AlreadyRatedException.class, ()-> {
            inscriptionService.rateSportEvent(inscription1.getInscriptionId(), user.getId(), 4);
        });
    }

    @Test
    public void testRateSportEvent() throws PermissionException, InstanceNotFoundException,
                                                ExpiredRateDateException, AlreadyRatedException {
        int users = 5, maxRate = 5, minRate = 1;
        boolean rated;
        List<Integer> rateList = new ArrayList();
        List<Inscription> inscriptionList = new ArrayList();
        List<User> userList = new ArrayList();
        SportEvent event1 = generateRandomSportEvent();
        int numSERated = event1.getNumberOfRates();
        int ratedSEValue = event1.getValueOfRates();
        for(int i = 0; i < users; i++) {
            rateList.add(new Random().nextInt(maxRate - minRate) + minRate);
            userList.add(signUpUser("user" + i));
            inscriptionList.add(addInscription(userList.get(i), event1, VALID_CREDIT_CARD,
                    LocalDateTime.of(2019, 1, 1, 10, 2, 3)));
            assertFalse(inscriptionList.get(i).isRated());

            inscriptionService.rateSportEvent(inscriptionList.get(i).getInscriptionId(),
                    userList.get(i).getId(), rateList.get(i));
            assertEquals(inscriptionList.get(i).getRateValue(), rateList.get(i));
            assertTrue(inscriptionList.get(i).isRated());
        }
        int maxRateValue = 0;
        for (int r_num:rateList) {
            if(r_num != 0) maxRateValue += r_num;
        }
        assertEquals(numSERated+users, event1.getNumberOfRates());
        assertEquals((maxRateValue+ratedSEValue)/(numSERated+users), event1.getValueOfRates()/event1.getNumberOfRates());
    }
}
