import React from 'react';
import {FormattedMessage} from 'react-intl';
import {FindSportingEvents, FindSportingEventsResult} from "../../catalog";

const Home = () => (
    <div className="text-center">
        <FormattedMessage id="project.app.Home.welcome"/>
        <br/>

        <div className="text-center">
            <br/>
            <FindSportingEvents/>
            <FindSportingEventsResult/>
        </div>
    </div>

);

export default Home;
