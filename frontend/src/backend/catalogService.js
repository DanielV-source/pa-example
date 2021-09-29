import {config, appFetch} from './appFetch';

export const findAllProvinces = (onSuccess) =>
    appFetch('/catalog/provinces', config('GET'), onSuccess);

export const findAllSportEventTypes = (onSuccess) =>
    appFetch('/catalog/types', config('GET'), onSuccess);

export const getSportEvent = (id, onSuccess) =>
    appFetch(`/catalog/sportevents/${id}`, config('GET'), onSuccess);

export const findSportEvent = ({provinceId, sportEventTypeId, dateStart, dateEnd, page},
                               onSuccess) => {

    let path = `/catalog/sportevents?page=${page}`;

    path += provinceId ? `&provinceId=${provinceId}` : "";
    path += sportEventTypeId ? `&sportEventTypeId=${sportEventTypeId}` : "";
    path += dateStart ? `&dateStart=${dateStart}` : "";
    path += dateEnd ? `&dateEnd=${dateEnd}` : "";

    appFetch(path, config('GET'), onSuccess);

}