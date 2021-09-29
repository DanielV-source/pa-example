import configureMockStore from 'redux-mock-store';
import thunk from 'redux-thunk';

import * as actions from './actions';
import backend from '../../backend';

const middlewares = [thunk]
const mockStore = configureMockStore(middlewares);

afterEach(() => backend.inscriptionService.enroll.mockRestore());

test('Enroll - success', () => {

    const id = 1;
    const dorsal = 1;
    const backendEnrollSpy = jest.spyOn(backend.inscriptionService, 'enroll').mockImplementation(
        (_creditCardNumber, _sportingEventId, onSuccess, _onErrors) =>
            onSuccess({dorsal: dorsal, id: id}));
    const sportingEventId = 1;
    const creditCardNumber = "1234567890123456";
    const onSuccess = jest.fn();
    const onErrors = jest.fn();
    const action = actions.enroll(creditCardNumber, sportingEventId, onSuccess, onErrors);
    const expectedActions = [actions.enrollCompleted({dorsal, id})];
    const store = mockStore({});

    store.dispatch(action);

    expect(backendEnrollSpy.mock.calls[0][0]).toEqual(creditCardNumber);
    expect(backendEnrollSpy.mock.calls[0][1]).toEqual(sportingEventId);
    expect(store.getActions()).toEqual(expectedActions);
    expect(onSuccess).toHaveBeenCalled();
    expect(onErrors).not.toHaveBeenCalled();

});

test('Enroll - backend errors', () => {

    const backendErrors = {globalError: "Some backend error"};

    jest.spyOn(backend.inscriptionService, 'enroll').mockImplementation(
        (_creditCardNumber, _sportingEventId, _onSuccess, onErrors) =>
            onErrors(backendErrors));

    const sportingEventId = 1;
    const creditCardNumber = "1234567890123456";
    const onSuccess = jest.fn();
    const onErrors = jest.fn();
    const action = actions.enroll(creditCardNumber, sportingEventId, onSuccess, onErrors);
    const expectedActions = [];
    const store = mockStore({});

    store.dispatch(action);

    // Assertions common to the "successful" use case are not repeated here.
    expect(store.getActions()).toEqual(expectedActions);
    expect(onSuccess).not.toHaveBeenCalled();
    expect(onErrors).toHaveBeenCalled();
    expect(onErrors.mock.calls[0][0]).toEqual(backendErrors);

});