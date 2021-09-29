package es.udc.paproject.backend.rest.controllers;

import static es.udc.paproject.backend.rest.dtos.InscriptionConversor.*;

import java.util.Locale;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.udc.paproject.backend.rest.dtos.*;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.entities.Inscription;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.InscriptionService;
import es.udc.paproject.backend.rest.common.ErrorsDto;

@RestController
@RequestMapping("/inscriptions")
public class InscriptionController {

    private final static String EXPIRED_ENROLL_EXCEPTION_CODE = "project.exceptions.ExpiredEnrollException";
    private final static String MAX_PARTICIPANTS_EXCEPTION_CODE = "project.exceptions.MaxParticipantsException";
    private final static String ALREADY_ENROLLED_EXCEPTION_CODE = "project.exceptions.AlreadyEnrolledException";
    private final static String EXPIRED_PICK_UP_EXCEPTION_CODE = "project.exceptions.ExpiredPickUpException";
    private final static String INSCRIPTION_CODE_EXCEPTION_CODE = "project.exceptions.InscriptionCodeException";
    private final static String DORSAL_ALREADY_TAKEN_EXCEPTION_CODE = "project.exceptions.DorsalAlreadyTakenException";
    private final static String CREDIT_CARD_NUMBER_EXCEPTION_CODE = "project.exceptions.CreditCardNumberException";
    private final static String ALREADY_RATED_EXCEPTION_CODE = "project.exceptions.AlreadyRatedException";
    private final static String EXPIRED_RATE_DATE_EXCEPTION_CODE = "project.exceptions.ExpiredRateDateException";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private InscriptionService inscriptionService;


    @ExceptionHandler(ExpiredEnrollException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleExpiredEnrollException(ExpiredEnrollException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(EXPIRED_ENROLL_EXCEPTION_CODE, null,
                EXPIRED_ENROLL_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(MaxParticipantsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleMaxParticipantsException(MaxParticipantsException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(MAX_PARTICIPANTS_EXCEPTION_CODE, null,
                MAX_PARTICIPANTS_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(AlreadyEnrolledException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleAlreadyEnrolledException(AlreadyEnrolledException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(ALREADY_ENROLLED_EXCEPTION_CODE,
                new Object[] {exception.getAlreadyEnrolled()}, ALREADY_ENROLLED_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(ExpiredPickUpException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleExpiredPickUpException(ExpiredPickUpException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(EXPIRED_PICK_UP_EXCEPTION_CODE, null,
                EXPIRED_PICK_UP_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(AlreadyRatedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleAlreadyRatedException(AlreadyRatedException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(ALREADY_RATED_EXCEPTION_CODE, null,
                ALREADY_RATED_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(ExpiredRateDateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleExpiredRateDateException(ExpiredRateDateException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(EXPIRED_RATE_DATE_EXCEPTION_CODE, null,
                EXPIRED_RATE_DATE_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(InscriptionCodeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleInscriptionCodeException(InscriptionCodeException exception, Locale locale){
        String errorMessage = messageSource.getMessage(INSCRIPTION_CODE_EXCEPTION_CODE, null,
                INSCRIPTION_CODE_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(DorsalAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleDorsalAlreadyTakenException(DorsalAlreadyTakenException exception, Locale locale){
        String errorMessage = messageSource.getMessage(DORSAL_ALREADY_TAKEN_EXCEPTION_CODE, null,
                DORSAL_ALREADY_TAKEN_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(CreditCardNumberException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleCreditCardNumberException(CreditCardNumberException exception, Locale locale){
        String errorMessage = messageSource.getMessage(CREDIT_CARD_NUMBER_EXCEPTION_CODE, null,
                CREDIT_CARD_NUMBER_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);
    }

    @GetMapping("/")
    public BlockDto<InscriptionDto> showInscriptions(@RequestAttribute Long userId,
                                               @RequestParam(defaultValue="0") int page) {

        Block<Inscription> orderBlock = inscriptionService.showInscriptions(userId, page, 2);

        return new BlockDto<>(toInscriptionDtos(orderBlock.getItems()), orderBlock.getExistMoreItems());

    }
    @PostMapping("/")
    public InscriptionIdDorsalDto enrollSportEvent(@RequestAttribute Long userId,
                                                   @Validated @RequestBody EnrollParamsDto params)
            throws InstanceNotFoundException, ExpiredEnrollException, MaxParticipantsException,
            AlreadyEnrolledException {

        return toInscriptionIdDorsalDto(inscriptionService.enrollSportEvent(userId, params.getCreditCardNumber(),
                params.getSportEventId()));

    }

    @PostMapping("/{inscriptionId}/rate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rateSportEvent(@RequestAttribute Long userId, @PathVariable Long inscriptionId,
                                         @Validated @RequestBody RateInscriptionDto params)
            throws InstanceNotFoundException, PermissionException, ExpiredRateDateException,
            AlreadyRatedException {
            inscriptionService.rateSportEvent(inscriptionId, userId, params.getRateValue());

    }

    @PostMapping("/pickup")
    public int pickUpDorsal(@Validated @RequestBody PickUpdDorsalDto params)
            throws InstanceNotFoundException, ExpiredPickUpException, InscriptionCodeException, DorsalAlreadyTakenException,
                CreditCardNumberException {
        return inscriptionService.pickUpDorsal(params.getInscriptionId(), params.getSportEventId(), params.getCreditCard());
    }

}
