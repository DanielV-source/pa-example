import * as actionTypes from './actionTypes';
import * as selectors from './selectors';
import backend from '../../backend';

const findAllProvincesCompleted = provinces => ({
        type: actionTypes.FIND_ALL_PROVINCES_COMPLETED,
        provinces
});

export const findAllProvinces = () => (dispatch, getState) => {
    const provinces = selectors.getProvinces(getState());

    if(!provinces) {
        backend.catalogService.findAllProvinces(
            provinces => dispatch(findAllProvincesCompleted(provinces))
        );
    }
}
const findSportEventByIdCompleted = sportEvent => ({
    type: actionTypes.FIND_SPORTING_EVENT_BY_ID_COMPLETED,
    sportEvent
});

export const findSportEventById = id => dispatch => {
    backend.catalogService.getSportEvent(id,
        sportEvent => dispatch(findSportEventByIdCompleted(sportEvent)));
}

export const clearSportEvent = () => ({
    type: actionTypes.CLEAR_SPORTING_EVENT
});

const findAllSportEventTypesCompleted = sportEventTypes => ({
        type: actionTypes.FIND_ALL_TYPES_COMPLETED,
        sportEventTypes
});

export const findAllSportEventTypes = () => (dispatch, getState) => {
    const sportEventTypes = selectors.getSportEventTypes(getState());

    if(!sportEventTypes) {
        backend.catalogService.findAllSportEventTypes(
            sportEventTypes => dispatch(findAllSportEventTypesCompleted(sportEventTypes))
        );
    }
}

export const findSportingEventsCompleted = sportingEventSearch => ({
        type: actionTypes.FIND_SPORTING_EVENT_COMPLETED,
        sportingEventSearch
});

export const findSportingEvents = criteria => dispatch => {
    dispatch(clearSportingEventSearch());
    backend.catalogService.findSportEvent(criteria,
        result => dispatch(findSportingEventsCompleted({criteria, result})));
}

export const previousFindSportingEventsResultPage = criteria =>
    findSportingEvents({...criteria, page: criteria.page-1});

export const nextFindSportingEventsResultPage = criteria =>
    findSportingEvents({...criteria, page: criteria.page+1});

export const clearSportingEventSearch = () => (
    {type: actionTypes.CLEAR_SPORTING_EVENT_SEARCH});

const findSportingEventByIdCompleted = sportingEvent => ({
        type: actionTypes.FIND_SPORTING_EVENT_BY_ID_COMPLETED,
        sportingEvent
});

export const findSportingEventById = id => dispatch => {
    backend.catalogService.getSportEvent(id,
        sportingEvent => dispatch(findSportingEventByIdCompleted(sportingEvent)));
}

export const clearSportingEvent = () => ({
    type: actionTypes.CLEAR_SPORTING_EVENT
});