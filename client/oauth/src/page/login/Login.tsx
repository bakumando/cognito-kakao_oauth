import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./Login.css";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log(`username: ${username}, Password: ${password}`);
  };

  // 카카오 소셜 로그인 클릭 이벤트 핸들러입니다.
  const handleKakaoLogin = () => {
    window.location.href = `${process.env.REACT_APP_KAKAO_AUTHORIZE_URI}?identity_provider=${process.env.REACT_APP_KAKAO_IDENTITY_PROVIDER}&redirect_uri=${process.env.REACT_APP_KAKAO_REDIRECT_URI}&response_type=code&client_id=${process.env.REACT_APP_KAKAO_CLIENT_ID}&scope=${process.env.REACT_APP_KAKAO_SCOPE}`;
  };

  // 네이버 소셜 로그인 클릭 이벤트 핸들러입니다.
  const handleNaverLogin = () => {
    window.location.href = `${process.env.REACT_APP_NAVER_AUTHORIZE_URI}e?identity_provider=${process.env.REACT_APP_NAVER_IDENTITY_PROVIDER}&redirect_uri=${process.env.REACT_APP_NAVER_REDIRECT_URI}&response_type=code&client_id=${process.env.REACT_APP_NAVER_CLIENT_ID}&scope=${process.env.REACT_APP_NAVER_SCOPE}`; // 네이버 소셜 로그인 링크를 입력합니다.
  };

  return (
    <div className="login-page">
      <div className="login-box">
        <h1>Login Page</h1>
        <form onSubmit={handleSubmit}>
          <input
            type="username"
            placeholder="계정명"
            value={username}
            onChange={handleUsernameChange}
            required
          />
          <input
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={handlePasswordChange}
            required
          />
          <button type="submit">로그인</button>
        </form>
        <div className="social-login">
          <button className="social-login-btn" onClick={handleKakaoLogin}>
            <img src={"icon/kakao.png"} alt="Kakao Logo" />
          </button>
          <button className="social-login-btn" onClick={handleNaverLogin}>
            <img src={"icon/naver.png"} alt="Naver Logo" />
          </button>
        </div>
        <p>
          계정이 없으신가요? <Link to="/signup">회원가입</Link>
        </p>
      </div>
    </div>
  );
};

export default Login;
