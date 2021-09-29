import {combineReducers} from 'redux';
import * as actionTypes from './actionTypes';

const initialState = {
    lastInscription: null,
    registrationSearch: null
};

const lastInscription = (state = initialState.lastInscription, action) => {

    switch (action.type) {

        case actionTypes.ENROLL_COMPLETED:
            return action.inscription;

        default:
            return state;

    }

}

const inscriptionSearch = (state = initialState.registrationSearch, action) => {

    switch (action.type) {

        case actionTypes.FIND_REGISTRATIONS_COMPLETED:
            return action.registrationSearch;

        case actionTypes.CLEAR_REGISTRATION_SEARCH:
            return initialState.registrationSearch;

        case actionTypes.SPORT_EVENT_RATED:
            return {
                criteria: state.criteria,
                result: {
                    items: state.result.items.map(registration => {
                            return registration.inscriptionId === Number(action.inscription.inscriptionId) ?
                        {...registration, rateValue: action.inscription.rateValue, rated: true} : registration}),
                    existMoreItems: state.result.existMoreItems
                        }
                }

        default:
            return state;
    }
}
const reducer = combineReducers({
    lastInscription,
    inscriptionSearch
});

export default reducer;