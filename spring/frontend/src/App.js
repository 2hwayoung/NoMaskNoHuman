import React from 'react';
import { Route, Link } from 'react-router-dom';
import Home from './component/Home';
import LiveVideo from './component/LiveVideo';

const App = () => {
  return (
    <div>
      <ul>
        <li>
          <Link to="/">홈</Link>
        </li>
        <li>
          <Link to="/livevideo">라이브 비디오</Link>
        </li>
      </ul>
      <hr />
      <Route path="/" exact={true} component={Home} />
      <Route path="/livevideo" component={LiveVideo} />
    </div>
  );
};

export default App;