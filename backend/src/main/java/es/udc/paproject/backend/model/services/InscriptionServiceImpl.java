package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;

import es.udc.paproject.backend.model.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class InscriptionServiceImpl implements InscriptionService {

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private InscriptionDao inscriptionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SportEventDao sportEventDao;


    @Override
    public Inscription enrollSportEvent(Long userId, String creditCardNumber, Long sportEventId)
            throws ExpiredEnrollException, MaxParticipantsException, AlreadyEnrolledException,
            InstanceNotFoundException {

        User user = permissionChecker.checkUser(userId); // throws InstanceNotFoundException
        SportEvent sportEvent = permissionChecker.checkSportEvent(sportEventId); // throws InstanceNotFoundException
        permissionChecker.checkIsAlreadyInscribed(userId, sportEventId); // throws AlreadyEnrolledException

        LocalDateTime t = sportEvent.getDate().minusDays(1);
        if(LocalDateTime.now().isAfter(t)) {
            throw new ExpiredEnrollException();
        }

        if (sportEvent.isFull()) {
            throw new MaxParticipantsException();
        } else {
            sportEvent.setNumParticipants(sportEvent.getNumParticipants()+1);

        }

        int num_dorsal = sportEvent.getNumParticipants(); //dorsal has the same participant number


        Inscription inscription = new Inscription(user, sportEvent, num_dorsal, creditCardNumber, false,
                false, 0, LocalDateTime.now());

        inscriptionDao.save(inscription);

        return inscription;
    }

    @Override
    public int pickUpDorsal(Long inscriptionId, Long sportEventId, String creditCardNumber)
        throws InstanceNotFoundException, ExpiredPickUpException, InscriptionCodeException, DorsalAlreadyTakenException,
        CreditCardNumberException {

        LocalDateTime now = LocalDateTime.now();
        Optional<SportEvent> optionalEvent = sportEventDao.findById(sportEventId);
        Optional<Inscription> optionalInscription = inscriptionDao.findById(inscriptionId);

        SportEvent event = null;
        Inscription inscription = null;

        if(optionalEvent.isPresent()){
            event = optionalEvent.get();
        } else {
            throw new InstanceNotFoundException("Sport event", sportEventId);
        }

        if(optionalInscription.isPresent()){
            inscription = optionalInscription.get();
        } else {
            throw new InstanceNotFoundException("Inscription", inscriptionId);
        }

        // Se verifica que faltan menos de 12 horas para el comienzo de la prueba
        if(now.compareTo(event.getDate().minusHours(12)) < 0){
            throw new ExpiredPickUpException();
        }
        // Se verifica que el código de inscripción está asociado al evento
        if(!inscription.getEvent().getEventId().equals(sportEventId)){
            throw new InscriptionCodeException();
        }
        // Se verifica que el dorsal no fue entregado anteriormente
        if(inscription.isCollected()){
            throw new DorsalAlreadyTakenException();
        }
        // Se verifica que la inscripción se hizo con esa tarjeta
        if(!inscription.getCreditCard().equals(creditCardNumber)){
            throw new CreditCardNumberException();
        }

        inscription.setCollected(true);
        inscriptionDao.save(inscription);
        return inscription.getDorsal();
    }

    @Override
    public void rateSportEvent(Long inscriptionId, Long userId, int rateValue)
        throws AlreadyRatedException, InstanceNotFoundException, PermissionException, ExpiredRateDateException {
        Optional<Inscription> optionalInscription = inscriptionDao.findById(inscriptionId);
        Optional<SportEvent> optionalSportEvent;
        Inscription inscription = null;
        int MAXDAYSDIFF = 15; //Máximo número de días para valorar

        if(optionalInscription.isPresent()) {
            inscription = optionalInscription.get();
        }else throw new InstanceNotFoundException("Inscription", inscriptionId);
        optionalSportEvent = Optional.ofNullable(inscription.getEvent());
        SportEvent event;
        LocalDateTime now = LocalDateTime.now();

        if(optionalSportEvent.isPresent()){
            event = optionalSportEvent.get();
        }else throw new InstanceNotFoundException("SportEvent", inscription.getEvent().getEventId());

        Long daysDiff = Duration.between(event.getDate(), now).toDays();
        int numRates = event.getNumberOfRates() + 1;
        int newRate = (event.getValueOfRates() + rateValue);
        if(inscription.getUser().getId().equals(userId)) {
            if(!inscription.isRated()) {
                if(daysDiff <= MAXDAYSDIFF && daysDiff >= 0){
                    inscription.setRateValue(rateValue);
                    inscription.setRated(true);
                    event.setValueOfRates(newRate);
                    event.setNumberOfRates(numRates);
                    sportEventDao.save(event);
                    inscriptionDao.save(inscription);
                }else throw new ExpiredRateDateException();
            }else throw new AlreadyRatedException();
        }else throw new PermissionException();
    }

    @Override
    @Transactional(readOnly=true)
    public Block<Inscription> showInscriptions(Long userId, int pag, int number) {

        Slice<Inscription> slice = inscriptionDao.findByUserIdOrderByDateDesc(userId, PageRequest.of(pag, number));
        return new Block<>(slice.getContent(), slice.hasNext());
    }

}
