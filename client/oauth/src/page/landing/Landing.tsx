import { Link } from "react-router-dom";
import "./Landing.css";

function Landing() {
  return (
    <div className="landing-container">
      <div className="landing-header">
        <h1>Beyond OAuth</h1>
      </div>
      <div className="landing-content">
        <h2>Welcome to Beyond Oauth!</h2>
        <div className="landing-buttons">
          <Link to="/login">Login</Link>
          <Link to="/register">Register</Link>
        </div>
      </div>
    </div>
  );
}

export default Landing;
