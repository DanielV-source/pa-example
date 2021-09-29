import React from 'react';
import {FormattedDate, FormattedMessage, FormattedTime} from 'react-intl';
import PropTypes from 'prop-types';
import {SportingEventLink} from '../../common';
import * as selectors from '../selectors';

const SportingEvents = ({sportingEvents, provinces, sportEventTypes}) => {
    const formattedRateValue = (rate, number) => {
        if(number === 0) return '-';
        else return (rate*1.0/number).toFixed(2);
    }
    return(

        <table className="table table-striped table-hover">

            <thead>
            <tr>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.name'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.type'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.province'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.date'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.global.fields.averageScore'/>
                </th>
            </tr>
            </thead>

            <tbody>
            {sportingEvents.map(sportingEvent =>
                <tr key={sportingEvent.eventId}>
                    <td><SportingEventLink id={sportingEvent.eventId} name={sportingEvent.name}/></td>
                    <td>{selectors.getSportEventTypeName(sportEventTypes, sportingEvent.type)}</td>
                    <td>{selectors.getProvinceName(provinces, sportingEvent.province)}</td>
                    <td>
                        <FormattedDate value={new Date(sportingEvent.date)}/> - <FormattedTime value={new Date(sportingEvent.date)}/>
                    </td>
                    <td>{formattedRateValue(sportingEvent.valueOfRates, sportingEvent.numberOfRates)}</td>
                </tr>
            )}
            </tbody>
        </table>
    )
};

SportingEvents.propTypes = {
    sportingEvents: PropTypes.array.isRequired,
    provinces: PropTypes.array.isRequired,
    sportEventTypes: PropTypes.array.isRequired
};

export default SportingEvents;
