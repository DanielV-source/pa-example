import React from 'react';

import {useParams} from 'react-router-dom';
import {BackLink} from '../../common';
import RateInscriptionForm from "./RateInscriptionForm";


const RateInscription = () => {
    const {id} = useParams();
    if (!id) {
        return null;
    }
    return (

        <div>
            <BackLink/>
            <RateInscriptionForm id = {id}/>

        </div>

    );
}

export default RateInscription;
