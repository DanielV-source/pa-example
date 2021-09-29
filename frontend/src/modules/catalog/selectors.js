const getModuleState = state => state.catalog;

export const getProvinces = state =>
    getModuleState(state).provinces;

export const getSportEventTypes = state =>
    getModuleState(state).sportEventTypes;

export const getSportingEventSearch = state =>
    getModuleState(state).sportingEventSearch;

export const getProvinceName = (provinces, id) => {
    if(!provinces) {
        return '';
    }
    const province = provinces.find(province => province.provinceId === id);

    if (!province) {
        return '';
    }
    return province.name;
}

export const getSportEventTypeName = (sportEventTypes, id) => {
    if(!sportEventTypes) {
        return '';
    }
    const sportEventType = sportEventTypes.find(sportEventType => sportEventType.typeId === id);

    if (!sportEventType) {
        return '';
    }
    return sportEventType.name;
}

export const getSportingEvent = state =>
    getModuleState(state).sportingEvent;

