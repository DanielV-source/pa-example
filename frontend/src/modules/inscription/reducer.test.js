import reducer from './reducer';
import * as actions from './actions';

test('ENROLL_COMPLETED', () => {

    const inscriptionId = 1;
    const dorsalId = 1;
    const inscription = {dorsalId, inscriptionId}

    const initialState = {lastInscription: null}


    const state = reducer(initialState, actions.enrollCompleted(inscription));

    expect(state.lastInscription).toEqual(inscription);
});