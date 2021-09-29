import * as actions from './actions';
import * as actionTypes from './actionTypes';
import reducer from './reducer';
import * as selectors from './selectors';

export {default as InscriptionForm} from './components/InscriptionForm';
export {default as DeliverDorsalForm} from './components/DeliverDorsalForm';
export {default as InscriptionResult} from './components/InscriptionResult';
export {default as RateInscription} from './components/RateInscription';
export {default as FindRegistrations} from './components/FindRegistrations';
export {default as FindRegistrationsResult} from './components/FindRegistrationsResult';

export default {actions, actionTypes, reducer, selectors};