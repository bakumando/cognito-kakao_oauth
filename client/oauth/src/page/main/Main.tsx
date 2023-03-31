import React, { useEffect, useState } from "react";
import { useCookies } from "react-cookie";
import { useHistory } from "react-router-dom";
import "./Main.css";
import axios from "axios";

function Main() {
  const history = useHistory();
  const [cookies, setCookie, removeCookie] = useCookies([
    "accessToken",
    "refreshToken",
  ]);
  const [username, setUsername] = useState("");

  useEffect(() => {
    const blueId = new URLSearchParams(window.location.search).get("blueId");
    const accessToken = new URLSearchParams(window.location.search).get(
      "accessToken"
    );
    const refreshToken = new URLSearchParams(window.location.search).get(
      "refreshToken"
    );
    if (blueId) setUsername(blueId);
    if (accessToken)
      setCookie("accessToken", accessToken, { path: "/", maxAge: 3600 });
    if (refreshToken)
      setCookie("refreshToken", refreshToken, {
        path: "/",
        maxAge: 3600 * 24 * 30,
      });
  }, []);

  const handleLogout = () => {
    removeCookie("accessToken");
    removeCookie("refreshToken");
    history.push("/");
  };

  const generateNewToken = async () => {
    const result = await axios.post(
      `http://localhost:8080/oauth/token/refresh`,
      {
        blueId: username,
        refresh_token: cookies.refreshToken,
      }
    );
    console.log(result);
  };

  // 유저 추가 프로필 업데이트 테스트 용
  const updateMember = async () => {
    const result = await axios.put(
      `http://localhost:8080/member/${username}`,
      {
        name: "박민수",
        birthdate: "1988-11-29",
        phone_number: `+8201071799190`,
      },
      {
        headers: {
          Authorization: `${cookies.accessToken}`,
        },
      }
    );
    console.log(result);
  };

  return (
    <div className="main-wrapper">
      <h1>Welcome, {username}!</h1>
      <p>You have successfully logged in to the beyond-test.</p>
      <div>
        <button className="logout-btn" onClick={handleLogout}>
          Logout
        </button>
      </div>
      <div>
        <button className="logout-btn" onClick={generateNewToken}>
          GenerateNewToken
        </button>
      </div>
      <div>
        <button className="logout-btn" onClick={updateMember}>
          updateProfile
        </button>
      </div>
    </div>
  );
}

export default Main;
