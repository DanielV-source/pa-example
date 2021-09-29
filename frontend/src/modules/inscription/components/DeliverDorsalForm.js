import React, {useState} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import catalog from '../../catalog';
import {Success, Errors} from '../../common';
import * as actions from '../actions';

const DeliverDorsalForm = () => {
    const dispatch = useDispatch();
    const sportingEventCode = useSelector(catalog.selectors.getSportingEvent).eventId;
    const [dorsal, setDorsal] = useState(null);
    const [inscriptionCode, setInscriptionCode] = useState(null);
    const [creditCard, setCreditCard] = useState('');
    const [backendErrors, setBackendErrors] = useState(null);
    let form;

    const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity()) {
            dispatch(actions.pickUpDorsal(inscriptionCode,
                sportingEventCode,
                creditCard,
                e => setDorsal(<FormattedMessage id="project.pickup.DeliverDorsalForm.dorsal" values={{ dorsal: e }}/>),
                errors => setBackendErrors(errors)));
        } else {
            setBackendErrors(null);
            form.classList.add('was-validated');
        }
    }

    return (

        <div>
            <Errors errors={backendErrors}
                    onClose={() => setBackendErrors(null)}/>
            <Success message= {dorsal} onClose={() => setDorsal(null)}/>

            <div className="card bg-light border-dark">
                <h5 className="card-header text-center">
                    <FormattedMessage id="project.pickup.DeliverDorsalForm.title"/>
                </h5>
                <div className="card-body">
                    <form ref={node => form = node}
                          className="needs-validation" noValidate
                          onSubmit={(e) => handleSubmit(e)}>
                        <div className="form-group row">
                            <label htmlFor="inscriptionCode" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.pickup.DeliverDorsalForm.inscriptionCode"/>
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="inscriptionCode" className="form-control"
                                       value={inscriptionCode}
                                       onChange={e => setInscriptionCode(e.target.value)}
                                       autoFocus
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="creditCard" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.pickup.DeliverDorsalForm.creditCardNumber"/>
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="creditCard" className="form-control"
                                       value={creditCard}
                                       onChange={e => setCreditCard(e.target.value)}
                                       autoFocus
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                             <div className="col-md-1">
                                <button type="submit" className="btn btn-primary">
                                    <FormattedMessage id="project.pickup.DeliverDorsalForm.deliverDorsalButton"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    );
}

export default DeliverDorsalForm;
