package es.udc.paproject.backend.model.services;

import java.util.Optional;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.AlreadyEnrolledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.InscriptionDao;

@Service
@Transactional(readOnly=true)
public class PermissionCheckerImpl implements PermissionChecker {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private SportEventDao sportEventDao;

	@Autowired
	private InscriptionDao inscriptionDao;
	
	@Override
	public void checkUserExists(Long userId) throws InstanceNotFoundException {
		
		if (!userDao.existsById(userId)) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}
		
	}

	@Override
	public User checkUser(Long userId) throws InstanceNotFoundException {

		Optional<User> user = userDao.findById(userId);
		
		if (!user.isPresent()) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}
		
		return user.get();
		
	}

	@Override
	public SportEvent checkSportEvent(Long sportEventId) throws InstanceNotFoundException {

		Optional<SportEvent> sportEvent = sportEventDao.findById(sportEventId);

		if (!sportEvent.isPresent()) {
			throw new InstanceNotFoundException("project.entities.sportEvent", sportEventId);
		}

		return sportEvent.get();

	}

	@Override
	public void checkIsAlreadyInscribed(Long userId, Long eventId) throws AlreadyEnrolledException {

		Optional<Inscription> inscription = inscriptionDao.findByUserIdAndEventEventId(userId, eventId);

		if(inscription.isPresent()) {
			throw new AlreadyEnrolledException(inscription.get().getInscriptionId());
		}
	}

}
