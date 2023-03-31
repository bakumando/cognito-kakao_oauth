import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Landing from "./page/landing/Landing";
import Login from "./page/login/Login";
import Main from "./page/main/Main";

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/" component={Landing} />
        <Route path="/login" component={Login} />
        <Route path="/main" component={Main} />
      </Switch>
    </Router>
  );
}

export default App;
