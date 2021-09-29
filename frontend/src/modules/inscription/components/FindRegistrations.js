import {useEffect} from 'react';
import {useDispatch} from 'react-redux';
import {useHistory} from 'react-router-dom';

import * as actions from '../actions';

const FindRegistrations = () => {

    const dispatch = useDispatch();
    const history = useHistory();

    useEffect(() => {
        dispatch(actions.findRegistrations({page: 0}));
        history.push('/inscriptions/find-registrations-result');
    });

    return null;
}

export default FindRegistrations;