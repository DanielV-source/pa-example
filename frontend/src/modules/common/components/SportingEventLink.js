import React from 'react';
import PropTypes from 'prop-types';

import {Link} from 'react-router-dom';

const SportingEventLink = ({id, name}) => {

    return (
        <Link to={`/catalog/sporting-event-details/${id}`}>
            {name}
        </Link>
    );

}

SportingEventLink.propTypes = {
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
};

export default SportingEventLink;