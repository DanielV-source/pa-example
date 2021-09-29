import React from 'react';
import {FormattedDate, FormattedMessage, FormattedTime} from 'react-intl';
import PropTypes from 'prop-types';
import {SportingEventLink} from '../../common';
import RateLink from "./RateLink";

const Registrations = ({registrations}) => {

    const formattedRateValue = (value) => {
        if(value === 0) return '-';
        else return value;
    }

    const formattedBool = (collected) => {
        if (collected){
            return <i>✓</i>
        } else {
            return <i>✕</i>
        }
    };

    const eventAlreadyHeld = (sportDate) => Date.now() >= sportDate;

    const dateIsValid = (sportDate) => Date.now() <= sportDate + (86400000*15); // 15 dias

    return (

    <table className="table table-striped table-hover">

        <thead>
        <tr className="text-center">
            <th scope="col">
                <FormattedMessage id='project.global.fields.date'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.inscriptionId'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.name'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.dorsal'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.creditCard'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.dorsalTaken'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.score'/>
            </th>
        </tr>
        </thead>

        <tbody>
        {registrations.map(registration =>
            <tr className="text-center" key={registration.inscriptionId}>
                <td>
                    <FormattedDate value={new Date(registration.date)}/> - <FormattedTime value={new Date(registration.date)}/>
                </td>
                <td>{registration.inscriptionId}</td>
                <td><SportingEventLink id={registration.sportEventId} name={registration.sportEventName}/></td>
                <td>{registration.dorsal}</td>
                <td>{registration.shortCreditCard}</td>
                <td>{formattedBool(registration.collected)}</td>
                <td>{formattedRateValue(registration.rateValue)}</td>

                {!registration.rated && eventAlreadyHeld(registration.sportEventDate) &&
                dateIsValid(registration.sportEventDate) && <td><RateLink inscription={registration}/></td>}
            </tr>

        )}
        </tbody>


    </table>

)};

Registrations.propTypes = {
    registrations: PropTypes.array.isRequired
};

export default Registrations;