import React from 'react';
import {createStore} from 'redux';
import {Provider} from 'react-redux';
import {render, fireEvent} from '@testing-library/react';
import {createMemoryHistory} from 'history'

import InscriptionForm from './InscriptionForm';
import {IntlProvider} from 'react-intl';
import messages from '../../../i18n/messages';
import {Router} from 'react-router-dom';
import * as actions from '../actions';

const renderComponent = (component, initialState={})=> {

    const store = createStore(() => initialState);
    store.dispatch = jest.fn();
    const history = createMemoryHistory();

    return {history, ...render(
        <Provider store={store}>
            <IntlProvider locale="en" messages={messages['en']}>
                <Router history={history}>
                    {component}
                </Router>
            </IntlProvider>
        </Provider>
    )};
}

afterEach(() => actions.enroll.mockRestore());

test('Enroll - success', () => {

    const sportingEventId = 1;
    const initialState = {inscription: {lastInscription: null}};
    const enrollSpy = jest.spyOn(actions, 'enroll').mockImplementation(
        (_creditCardNumber, _sportingEventId,onSuccess, _onErrors) =>
            onSuccess());

    const {getByLabelText, getByRole, history} = renderComponent(<InscriptionForm  sportingEventId={sportingEventId}/>,
        initialState);
    const creditCardNumberInput = getByLabelText('Credit card number');
    const enrollButton = getByRole('button');
    const creditCardNumber = "1234567890123456";

    fireEvent.change(creditCardNumberInput, {target: {value: creditCardNumber}});

    expect(creditCardNumberInput.value).toEqual(creditCardNumber);

    fireEvent.click(enrollButton);

    expect(enrollSpy.mock.calls[0][0]).toEqual(creditCardNumber);
    expect(enrollSpy.mock.calls[0][1]).toEqual(sportingEventId);
    expect(history.length).toEqual(2);
    expect(history.location.pathname).toEqual('/inscriptions/enroll-completed');

});

test('Enroll - backend errors', () => {

    const sportingEventId = 1;
    const backendError = "Some backend error";
    const initialState = {inscription: {lastInscription: null}};
    const enrollSpy = jest.spyOn(actions, 'enroll').mockImplementation(
        (_creditCardNumber, _sportingEventId, _onSuccess, onErrors) =>
            onErrors({globalError: backendError}));

    const {getByLabelText, getByRole, container, history} = renderComponent(<InscriptionForm  sportingEventId={sportingEventId}/>,
        initialState);
    const creditCardNumberInput = getByLabelText('Credit card number');
    const enrollButton = getByRole('button');
    const creditCardNumber = "1234567890123456";

    fireEvent.change(creditCardNumberInput, {target: {value: creditCardNumber}});

    expect(creditCardNumberInput.value).toEqual(creditCardNumber);

    fireEvent.click(enrollButton);

    // Assertions common to the "successful" use case are not repeated here.
    expect(container).toHaveTextContent(backendError);
    expect(history.length).toEqual(1);
    expect(history.location.pathname).toEqual('/');

});