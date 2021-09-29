import React, {useState} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import {Errors, Success} from '../../common';
import * as selectors from '../selectors';
import * as actions from '../actions';

const RateInscriptionForm = (inscriptionId) => {
    const dispatch = useDispatch();
    const [rateValue, setRateValue] = useState(1);
    const [backendErrors, setBackendErrors] = useState(null);
    const [success, setSuccess] = useState(null);
    const inscriptionSearch = useSelector(selectors.getInscriptionSearch);
    let form;

        const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity()) {

            dispatch(actions.rateSportEvent(inscriptionId.id, rateValue,
                () => setSuccess(<FormattedMessage id="project.inscription.rateInscription.rated"/>),
                errors => setBackendErrors(errors)));

        } else {
            setBackendErrors(null);
            form.classList.add('was-validated');
        }

    }
    if(inscriptionSearch != null) {
        return (

                <div>
                    <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
                    <Success message= {success} onClose={() => setSuccess(null)}/>
                    <div className="text-center"><h3>
                        <FormattedMessage id="project.inscription.rateInscription.rating"/> {selectors.getSportEventName(inscriptionSearch, Number(inscriptionId.id))}
                    </h3></div>
                    <br/>

                    <form ref={node => form = node} className="needs-validation" noValidate
                          onSubmit={e => handleSubmit(e)}>
                        <div className="form-group row">
                            <label htmlFor="quantity" className="offset-md-5 col-md-1 col-form-label">
                                <FormattedMessage id="project.global.fields.rateValue"/>
                            </label>
                            <div className="col-md-1">
                                <input type="number" id="quantity" className="form-control"
                                       value={rateValue}
                                       onChange={e => setRateValue(Number(e.target.value))}
                                       autoFocus
                                       min="1"
                                       max="5"/>

                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.incorrectRateValue'/>
                                </div>
                            </div>
                            <button type="submit" className="btn btn-primary">
                                <FormattedMessage id="project.global.buttons.rate"/>
                            </button>
                        </div>
                    </form>
                </div>

            );
    }
    return null;

}

export default RateInscriptionForm;
