import {combineReducers} from 'redux';

import app from '../modules/app';
import users from '../modules/users';
import catalog from '../modules/catalog';
import inscription from '../modules/inscription';

const rootReducer = combineReducers({
    app: app.reducer,
    users: users.reducer,
    catalog: catalog.reducer,
    inscription: inscription.reducer
});

export default rootReducer;
