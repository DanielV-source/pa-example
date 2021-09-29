import React from 'react';
import {useSelector} from 'react-redux';
import {Route, Switch} from 'react-router-dom';

import AppGlobalComponents from './AppGlobalComponents';
import Home from './Home';
import {Login, SignUp, UpdateProfile, ChangePassword, Logout} from '../../users';
import users from '../../users';

import {FindSportingEventsResult, SportingEventDetails} from '../../catalog';
import {InscriptionForm, InscriptionResult, FindRegistrations, FindRegistrationsResult, RateInscription} from "../../inscription";


const Body = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const userRole = useSelector(users.selectors.getRole);
    
   return (

        <div className="container">
            <br/>
            <AppGlobalComponents/>
            <Switch>
                <Route exact path="/"><Home/></Route>
                <Route exact path="/catalog/find-sporting-event-result"><FindSportingEventsResult/></Route>
                <Route exact path="/catalog/sporting-event-details/:id"><SportingEventDetails/></Route>
                {loggedIn && userRole !== "EMPLOYEE" && <Route exact path="/inscriptions/enroll"><InscriptionForm/></Route>}
                {loggedIn && userRole !== "EMPLOYEE" && <Route exact path="/inscriptions/enroll-completed"><InscriptionResult/></Route>}
                {loggedIn && userRole !== "EMPLOYEE" && <Route exact path="/inscriptions/find-registrations"><FindRegistrations/></Route>}
                {loggedIn && userRole !== "EMPLOYEE" && <Route exact path="/inscriptions/find-registrations-result"><FindRegistrationsResult/></Route>}
                {loggedIn && userRole !== "EMPLOYEE" && <Route exact path="/inscriptions/rate/:id"><RateInscription/></Route>}
                {loggedIn && <Route exact path="/users/update-profile"><UpdateProfile/></Route>}
                {loggedIn && <Route exact path="/users/change-password"><ChangePassword/></Route>}
                {loggedIn && <Route exact path="/users/logout"><Logout/></Route>}
                {!loggedIn && <Route exact path="/users/login"><Login/></Route>}
                {!loggedIn && <Route exact path="/users/signup"><SignUp/></Route>}
                <Route><Home/></Route>
            </Switch>
        </div>

    );

};

export default Body;
