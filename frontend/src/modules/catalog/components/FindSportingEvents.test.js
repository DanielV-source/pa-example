import React from 'react';
import {createStore} from 'redux';
import {Provider} from 'react-redux';
import {render, fireEvent} from '@testing-library/react';
import {createMemoryHistory} from 'history'

import FindSportingEvents from "./FindSportingEvents";
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


test('findSportingEvents - success', () => {

    const provinceId = "1";
    const sportEventId = "1";
    const nameP = "Ourense";
    const nameT = "Ciclismo";
    const initialState = {catalog: {provinces: [{provinceId: provinceId, name: nameP}],
                                    sportEventTypes: [{typeId: sportEventId, name: nameT}]}};

    const findSportingEventsSpy = jest.spyOn(actions, 'findSportingEvents').mockImplementation(
        ({provinceId: _provinceId, sportEventTypeId: _sportEventId, dateStart: _dateStart, dateEnd: _dateEnd,
             page: _0}) => {});

    const {getByLabelText, getByRole, history} = renderComponent(<FindSportingEvents/>, initialState);
    const firstDateInput = getByLabelText('Start date');
    const lastDateInput = getByLabelText('Last date');
    const provinceInput = getByLabelText('Province');
    const eventTypeInput = getByLabelText('Event type');
    const searchButton = getByRole('button');


    const dateStart = "2020-06-09";
    const dateEnd = "2022-06-09";

    fireEvent.change(firstDateInput, {target: {value: dateStart}});
    fireEvent.change(lastDateInput, {target: {value: dateEnd}});
    fireEvent.change(provinceInput, {target: {value: provinceId}});
    fireEvent.change(eventTypeInput, {target: {value: sportEventId}});

    expect(firstDateInput.value).toEqual(dateStart);
    expect(lastDateInput.value).toEqual(dateEnd);
    expect(provinceInput.value).toEqual(provinceId);
    expect(eventTypeInput.value).toEqual(sportEventId);

    fireEvent.click(searchButton);

    expect(findSportingEventsSpy.mock.calls[0][0].provinceId).toEqual(provinceId);
    expect(findSportingEventsSpy.mock.calls[0][0].sportEventTypeId).toEqual(sportEventId);
    expect(findSportingEventsSpy.mock.calls[0][0].dateStart).toEqual(dateStart);
    expect(findSportingEventsSpy.mock.calls[0][0].dateEnd).toEqual(dateEnd);
    expect(history.length).toEqual(2);
    expect(history.location.pathname).toEqual('/catalog/find-sporting-events-result');

});

