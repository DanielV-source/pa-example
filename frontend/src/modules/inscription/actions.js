import backend from '../../backend';
import * as actionTypes from './actionTypes';

export const enrollCompleted = (inscription) => ({
    type: actionTypes.ENROLL_COMPLETED,
    inscription
});

export const enroll = (creditCardNumber, sportingEventId, onSuccess,
                       onErrors) => dispatch =>
    backend.inscriptionService.enroll(creditCardNumber, sportingEventId, ({id, dorsal}) => {
            dispatch(enrollCompleted({id, dorsal}));
            onSuccess();
        },
        onErrors);

export const pickUpDorsal = (inscriptionCode, sportingEventCode, creditCard, onSuccess,
                       onErrors) => dispatch =>
    backend.inscriptionService.pickUpDorsal(inscriptionCode, sportingEventCode, creditCard, (dorsal) => {
            onSuccess(dorsal);
        },
        onErrors);

const updateRegistration = (inscription) => ({
    type: actionTypes.SPORT_EVENT_RATED,
    inscription
});

export const rateSportEvent = (inscriptionId, rateValue,
                                  onSuccess, onErrors) => dispatch =>
    backend.inscriptionService.rateSportEvent(inscriptionId, rateValue,
        () => {onSuccess();
        dispatch(updateRegistration({inscriptionId, rateValue}))},
        onErrors);

export const findRegistrationsCompleted = registrationSearch => ({
    type: actionTypes.FIND_REGISTRATIONS_COMPLETED,
    registrationSearch
});

const clearRegistrationSearch = () => ({
    type: actionTypes.CLEAR_REGISTRATION_SEARCH
});

export const findRegistrations = criteria => dispatch => {
    dispatch(clearRegistrationSearch());
    backend.inscriptionService.findInscriptions(criteria,
        result => dispatch(findRegistrationsCompleted({criteria, result})));
}

export const previousFindRegistrationsResultPage = criteria =>
    findRegistrations({page: criteria.page-1});

export const nextFindRegistrationsResultPage = criteria =>
    findRegistrations({page: criteria.page+1});

