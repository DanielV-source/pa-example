import React, {useState} from 'react';
import {useDispatch} from 'react-redux';
import {useHistory} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';

import ProvinceSelector from "./ProvinceSelector";
import * as actions from '../actions';
import TypeSelector from "./TypeSelector";

const FindSportingEvents = () => {

    const dispatch = useDispatch();
    const history = useHistory();
    const [dateStart, setDateStart] = useState('');
    const [dateEnd, setDateEnd] = useState('');
    const [provinceId, setProvinceId] = useState('');
    const [typeId, setTypeId] = useState('');

    const handleSubmit = event => {
        event.preventDefault();
        dispatch(actions.findSportingEvents(
            {provinceId: provinceId, sportEventTypeId: typeId, dateStart: dateStart,
            dateEnd: dateEnd, page: 0})
        );
        history.push('/catalog/find-sporting-events-result');
    }


    return (

        <form className="form-inline" onSubmit={e => handleSubmit(e)}>
            <div className="form-row">
                <div className="col-form-label">
                    <label htmlFor="dateStart"><FormattedMessage id='project.global.fields.firstDate'/></label>
                    <input id="dateStart" type="date" title="Fecha inicio" className="form-control"
                           value={dateStart} onChange={e => setDateStart(e.target.value)}/>
                </div>
                <div className="col-form-label">
                    <label htmlFor="dateEnd"><FormattedMessage id='project.global.fields.lastDate'/></label>
                    <input id="dateEnd" type="date" className="form-control"
                           value={dateEnd} onChange={e => setDateEnd(e.target.value)}/>
                </div>
                <div className="col-form-label">
                    <label htmlFor="provinceId"><FormattedMessage id='project.global.fields.province'/></label>
                    <ProvinceSelector id="provinceId" className="custom-select my-1 mr-sm-2"
                                      value={provinceId} onChange={e => setProvinceId(e.target.value)}/>
                </div>
                <div className="col-form-label">
                    <label htmlFor="typeId"><FormattedMessage id='project.global.fields.type'/></label>
                    <TypeSelector id="typeId" className="custom-select my-1 mr-sm-2"
                                  value={typeId} onChange={e => setTypeId(e.target.value)}/>
                </div>
                <div className="col-form-label">
                    <br/>
                    <button type="submit" className="btn btn-primary my-2 my-sm-0">
                        <FormattedMessage id='project.global.buttons.search'/>
                    </button>
                </div>

            </div>
        </form>
    )
}

export default FindSportingEvents;