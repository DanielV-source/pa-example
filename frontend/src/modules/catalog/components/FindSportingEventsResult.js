import React from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as selectors from '../selectors';
import * as actions from '../actions';
import {Pager} from '../../common';
import SportingEvents from './SportingEvents';

const FindSportingEventsResult = () => {

    const sportingEventSearch = useSelector(selectors.getSportingEventSearch);
    const provinces = useSelector(selectors.getProvinces);
    const sportEventTypes = useSelector(selectors.getSportEventTypes);
    const dispatch = useDispatch();

    if(!sportingEventSearch) {
        return null;
    }

    if(sportingEventSearch.result.items.length === 0) {
        return (
            <div className="alert alert-danger" role="alert">
                <FormattedMessage id='project.catalog.FindProductsResult.noSportEventsFound'/>
            </div>
        );
    }

    return (
        <div>
            <br/>
            <SportingEvents sportingEvents={sportingEventSearch.result.items} provinces={provinces}
                sportEventTypes={sportEventTypes}/>
            <Pager
                back={
                    {
                        enabled: sportingEventSearch.criteria.page >= 1,
                        onClick: () =>
                            dispatch(actions.previousFindSportingEventsResultPage(sportingEventSearch.criteria))
                    }
                }
                next={
                    {
                        enabled: sportingEventSearch.result.existMoreItems,
                        onClick: () =>
                            dispatch(actions.nextFindSportingEventsResultPage(sportingEventSearch.criteria))
                    }
                }/>
        </div>
    );

}

export default FindSportingEventsResult;