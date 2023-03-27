import React, { useEffect } from "react";
import { useCookies } from "react-cookie";
import { useHistory } from "react-router-dom";
import "./Main.css";

type Props = {
  username: string;
  onLogout: () => void;
};

function Main({ username }: Props) {
  const history = useHistory();
  const [cookies, setCookie] = useCookies();

  const handleLogout = () => {
    localStorage.clear();
    history.push("/");
  };

  return (
    <div className="main-wrapper">
      <h1>Welcome, {username}!</h1>
      <p>You have successfully logged in to the system.</p>
      <button className="logout-btn" onClick={handleLogout}>
        Logout
      </button>
    </div>
  );
}

export default Main;
