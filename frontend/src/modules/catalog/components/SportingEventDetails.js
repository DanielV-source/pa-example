import React, {useEffect} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedDate, FormattedMessage, FormattedNumber, FormattedTime} from 'react-intl';
import {useParams} from 'react-router-dom';

import users from '../../users';
import * as selectors from '../selectors';
import * as actions from '../actions';
import {BackLink} from '../../common';
import {InscriptionForm, DeliverDorsalForm} from "../../inscription";

const SportingEventDetails = () => {
    const userRole = useSelector(users.selectors.getRole);
    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const sportingEvent = useSelector(selectors.getSportingEvent);
    const dispatch = useDispatch();
    const provinces = useSelector(selectors.getProvinces);
    const types = useSelector(selectors.getSportEventTypes);
    const {id} = useParams();

    const formattedRateValue = (rate, number) => {
        if(number === 0) return '-';
        else return (rate*1.0/number).toFixed(2);
    }


    useEffect(() => {

        const eventId = Number(id);

        if (!Number.isNaN(eventId)) {
            dispatch(actions.findSportingEventById(eventId));
        }

        return () => dispatch(actions.clearSportingEvent());

    }, [id, dispatch]);

    if (!sportingEvent) {
        return null;
    }

    const isNotFull = () => sportingEvent.numParticipants !== sportingEvent.maxParticipants;

    const dateIsValid = () => Date.now() <= sportingEvent.date - 86400000; // 24 horas

    const deliverDorsalDate = () => (Date.now() <= sportingEvent.date && Date.now() >= sportingEvent.date - 43200000); // 12 horas

    return (

        <div>

            <BackLink/>

            <div className="card text-center">
                <div className="card-body">
                    <h5 className="card-title"> {sportingEvent.name} : {selectors.getSportEventTypeName(types, sportingEvent.type)}</h5>
                    <p className="card-text">{sportingEvent.description}</p>
                    <p className="card-text">
                        <FormattedMessage id='project.global.fields.average'/>
                        : {formattedRateValue(sportingEvent.valueOfRates, sportingEvent.numberOfRates)}
                    </p>
                    <p className="card-text">
                        <FormattedMessage id='project.global.fields.location'/>
                        : {sportingEvent.location}
                    </p>
                    <p className="card-text">
                        <FormattedMessage id='project.global.fields.province'/>
                        : {selectors.getProvinceName(provinces, sportingEvent.province)}
                    </p>
                    <p>
                        <FormattedMessage id='project.global.fields.date'/>
                        : <FormattedDate value={new Date(sportingEvent.date)}/> - <FormattedTime value={new Date(sportingEvent.date)}/>
                    </p>
                    <p className="card-text">
                        <FormattedMessage id='project.global.fields.maxParticipants'/>
                        : <FormattedNumber value={sportingEvent.maxParticipants}/>
                    </p>
                    <p className="card-text">
                        <FormattedMessage id='project.global.fields.numParticipants'/>
                        : <FormattedNumber value={sportingEvent.numParticipants}/>
                    </p>
                    <p className="card-text font-weight-bold">
                        <FormattedMessage id='project.global.fields.price'/>
                        : <FormattedNumber value={sportingEvent.price}/>€
                    </p>

                </div>
            </div>

            {loggedIn && userRole === "EMPLOYEE" && deliverDorsalDate() &&
            <div>
                <br/>
                <DeliverDorsalForm/>
            </div>
            }
            {loggedIn && (userRole === "USER") && isNotFull() && dateIsValid() &&
            <div>
                <br/>
                <InscriptionForm sportingEventId={sportingEvent.eventId}/>
            </div>
            }
        </div>

    );


}

export default SportingEventDetails;