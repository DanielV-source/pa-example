package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.exceptions.AlreadyEnrolledException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.AlreadyEnrolledException;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.entities.SportEvent;


public interface PermissionChecker {
	
	public void checkUserExists(Long userId) throws InstanceNotFoundException;
	
	public User checkUser(Long userId) throws InstanceNotFoundException;

	public SportEvent checkSportEvent(Long sportEventId) throws InstanceNotFoundException;

	public void checkIsAlreadyInscribed(Long userId, Long eventId) throws AlreadyEnrolledException;
}
