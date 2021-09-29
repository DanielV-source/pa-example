import React from 'react';
import {useSelector} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as selectors from '../selectors';
import {BackLink} from '../../common';

const InscriptionResult = () => {

    const inscription = useSelector(selectors.getLastInscription);

    if (!inscription) {
        return null;
    }

    return (
        <div>
            <BackLink/>

            <div className="alert alert-success" role="alert">
                &nbsp;
                <p> <FormattedMessage id="project.inscription.InscriptionResult.InscriptionId"/> : {inscription.id}</p>
                <p> <FormattedMessage id="project.inscription.InscriptionResult.dorsal"/> : {inscription.dorsal}</p>

            </div>
        </div>

    );

}

export default InscriptionResult;