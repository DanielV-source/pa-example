import {config, appFetch} from './appFetch';

export const enroll = (creditCardNumber, sportEventId ,onSuccess, onErrors) =>
    appFetch(`/inscriptions/`,
        config('POST', {creditCardNumber, sportEventId}), onSuccess, onErrors);

export const pickUpDorsal = (inscriptionId, sportEventId, creditCard, onSuccess, onErrors) =>
    appFetch(`/inscriptions/pickup`,
        config('POST', {inscriptionId, sportEventId, creditCard}), onSuccess, onErrors);

export const rateSportEvent = (inscriptionId, rateValue, onSuccess, onErrors) =>
    appFetch(`/inscriptions/${inscriptionId}/rate`,
        config('POST', {rateValue}), onSuccess, onErrors);

export const findInscriptions = ({page}, onSuccess) =>
    appFetch(`/inscriptions/?page=${page}`, config('GET'), onSuccess);
