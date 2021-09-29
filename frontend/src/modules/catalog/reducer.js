import {combineReducers} from 'redux';
import * as actionTypes from './actionTypes';

const initialState = {
    provinces: null,
    sportEventTypes: null,
    sportingEventSearch: null,
    sportingEvent: null
};

const provinces = (state = initialState.provinces, action) => {
    switch (action.type) {
        case actionTypes.FIND_ALL_PROVINCES_COMPLETED:
            return action.provinces;
        default:
            return state;
    }
}

const sportEventTypes = (state = initialState.sportEventTypes, action) => {
    switch (action.type) {
        case actionTypes.FIND_ALL_TYPES_COMPLETED:
            return action.sportEventTypes;
        default:
            return state;
    }
}


const sportingEventSearch = (state = initialState.sportingEventSearch, action) => {

    switch (action.type) {
        case actionTypes.FIND_SPORTING_EVENT_COMPLETED:
            return action.sportingEventSearch;

        case actionTypes.CLEAR_SPORTING_EVENT_SEARCH:
            return initialState.sportingEventSearch;

        default:
            return state;
    }
}

const sportingEvent = (state = initialState.sportingEvent, action) => {

    switch (action.type) {

        case actionTypes.FIND_SPORTING_EVENT_BY_ID_COMPLETED:
            return action.sportingEvent;

        case actionTypes.CLEAR_SPORTING_EVENT:
            return initialState.sportingEvent;

        default:
            return state;

    }
}

const reducer = combineReducers({
    provinces,
    sportEventTypes,
    sportingEventSearch,
    sportingEvent
});

export default reducer;