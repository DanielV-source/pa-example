import configureMockStore from 'redux-mock-store';
import thunk from 'redux-thunk';

import * as actions from './actions';
import backend from '../../backend';

const middlewares = [thunk]
const mockStore = configureMockStore(middlewares);

test('findSportingEvents - success', () => {

    const criteria = {provinceId: "1", sportEventTypeId: "1", dateStart: "2020-06-09",
        dateEnd: "2022-06-09", page: 0};
    const result = {
        items: [],
        existMoreItems: false
    };

    const backendFindSportEventSpy = jest.spyOn(backend.catalogService, 'findSportEvent').mockImplementation(
        (_criteria, onSuccess) =>
            onSuccess(result));

    const action = actions.findSportingEvents(criteria);
    const expectedActions = [actions.clearSportingEventSearch(), actions.findSportingEventsCompleted({criteria, result})];
    const store = mockStore({});

    store.dispatch(action);

    expect(backendFindSportEventSpy.mock.calls[0][0]).toEqual(criteria);
    expect(backendFindSportEventSpy.mock.calls[0][0].provinceId).toEqual(criteria.provinceId);
    expect(backendFindSportEventSpy.mock.calls[0][0].sportEventTypeId).toEqual(criteria.sportEventTypeId);
    expect(backendFindSportEventSpy.mock.calls[0][0].dateStart).toEqual(criteria.dateStart);
    expect(backendFindSportEventSpy.mock.calls[0][0].dateStart).toEqual(criteria.dateStart);
    expect(backendFindSportEventSpy.mock.calls[0][0].page).toEqual(criteria.page);

    expect(store.getActions()).toEqual(expectedActions);

});