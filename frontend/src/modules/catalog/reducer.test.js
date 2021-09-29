import reducer from './reducer';
import * as actions from './actions';

test('FIND_SPORTING_EVENT_COMPLETED', () => {

    const initialState = {};    //para el search no necesitamos el estado anterior

    const criteria = {provinceId: "1", sportEventTypeId: "1", dateStart: "2020-06-09",
        dateEnd: "2022-06-09", page: 0};
    const result = {
        items: [],
        existMoreItems: false
    };
    const search = {criteria, result};
    const state = reducer(initialState, actions.findSportingEventsCompleted(search));

    expect(state.sportingEventSearch).toEqual(search);
    expect(state.sportingEventSearch.criteria).toEqual(criteria);
    expect(state.sportingEventSearch.result).toEqual(result);
});