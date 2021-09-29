import React from 'react';
import {Link} from 'react-router-dom';
import PropTypes from 'prop-types';
import {FormattedMessage} from "react-intl";

const RateLink = ({inscription}) => {

    return (
        <Link to={`/inscriptions/rate/${inscription.inscriptionId}`}>
            <button type="submit" className="btn btn-primary">
                <FormattedMessage id="project.global.buttons.rate"/>
            </button>
        </Link>
    );

}

RateLink.propTypes = {
    inscription: PropTypes.object.isRequired
};

export default RateLink;