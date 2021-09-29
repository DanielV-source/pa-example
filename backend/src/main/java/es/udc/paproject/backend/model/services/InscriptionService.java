package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Inscription;
import es.udc.paproject.backend.model.exceptions.*;

public interface InscriptionService {
    Inscription enrollSportEvent(Long userId, String creditCardNumber, Long sportEventId)
            throws ExpiredEnrollException, 	//Expiró el tiempo para registrarse
            MaxParticipantsException, 		//Se alcanzó el límite de participantes
            AlreadyEnrolledException,		//Ya estaba inscrito
            InstanceNotFoundException; 		//o una similar

    Block<Inscription> showInscriptions(Long userId, int page, int number);

    int pickUpDorsal(Long inscriptionId, Long sportEventId, String creditCardNumber)
            throws InstanceNotFoundException,   //No se encuentra evento o inscripción
            ExpiredPickUpException,	            //Expiró el plazo de entrega de dorsal
            InscriptionCodeException,		    //Error código de inscripción
            DorsalAlreadyTakenException,	    //Dorsal ya entregado
            CreditCardNumberException;    	    //No coincide con la de inscription

    void rateSportEvent(Long inscriptionId, Long userId, int rateValue)
            throws AlreadyRatedException,	            //Ya valorada
            InstanceNotFoundException,          //No se encuentra
            PermissionException,                //Error de permisos
            ExpiredRateDateException;		    //Expiró el plazo de 15 días para valorarla (Fuera de plazo)

}
