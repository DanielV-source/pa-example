import React from 'react';
import {useSelector} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import PropTypes from 'prop-types';

import * as selectors from '../selectors';

const TypeSelector = (selectProps) => {

    const types = useSelector(selectors.getSportEventTypes);
    return (

        <select {...selectProps}>

            <FormattedMessage id='project.catalog.TypeSelector.allTypes'>
                {message => (<option value="">{message}</option>)}
            </FormattedMessage>

            {types && types.map(type =>
                <option key={type.typeId} value={type.typeId}>{type.name}</option>
            )}

        </select>

    );

}

TypeSelector.propTypes = {
    selectProps: PropTypes.object
};

export default TypeSelector;