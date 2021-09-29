import React from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as actions from '../actions';
import * as selectors from '../selectors';
import {Pager} from '../../common';
import Registrations from './Registrations';

const FindRegistrationsResult = () => {

    const inscriptionSearch = useSelector(selectors.getInscriptionSearch);
    const dispatch = useDispatch();

    if(!inscriptionSearch) {
        return null;
    }

    if (inscriptionSearch.result.items.length === 0) {
        return (
            <div className="alert alert-info" role="alert">
                <FormattedMessage id='project.inscription.FindRegistrationsResult.noInscriptions'/>
            </div>
        );
    }

    return (

        <div>
            <Registrations registrations={inscriptionSearch.result.items}/>
            <Pager
                back={{
                    enabled: inscriptionSearch.criteria.page >= 1,
                    onClick: () => dispatch(actions.previousFindRegistrationsResultPage(inscriptionSearch.criteria))
                }}
                next={{
                    enabled: inscriptionSearch.result.existMoreItems,
                    onClick: () => dispatch(actions.nextFindRegistrationsResultPage(inscriptionSearch.criteria))
                }}/>
        </div>
    );
}

export default FindRegistrationsResult;