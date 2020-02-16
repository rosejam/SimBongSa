import React from 'react'
import { Tab } from 'semantic-ui-react'
import CalendarContainer from 'containers/calendar/CalendarContainer';
import Location from 'containers/location/Location';
import VolListPage from 'containers/vollistpage/VolListPage';
const panes = [
    { menuItem: '리스트', render: () => <Tab.Pane><VolListPage /></Tab.Pane> },
    { menuItem: '지도', render: () => <Tab.Pane><Location /></Tab.Pane> },
    { menuItem: '달력', render: () => <Tab.Pane><CalendarContainer /></Tab.Pane> }
]

const TabExampleBasic = () => <div id="tab"><Tab panes={panes} /></div>

export default TabExampleBasic