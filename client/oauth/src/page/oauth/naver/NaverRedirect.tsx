import axios from "axios";
import { useEffect } from "react";
import { useCookies } from "react-cookie";
import { useHistory } from "react-router-dom";

function NaverRedirect() {
  const code = new URL(window.location.href).searchParams.get("code"); // 현재 URL에서 코드만 추출
  const [cookies, setCookie] = useCookies();
  const navigate = useHistory();

  // 컴포넌트가 마운트되면 로그인 로직 실행
  useEffect(() => {
    async function NaverLogin() {
      const res = await axios.get(
        process.env.REACT_APP_API +
          `/api/member/login/naver?code=${code}&state=${process.env.NAVER_STATE}`
      ); // 이 부분은 서버 API에 따라 바뀔 수 있으니 API 명세서를 잘 확인하세요.
      const ACCESS_TOKEN = res.headers["authorization"];
      const REFRESH_TOKEN = res.headers["refresh-token"];
      setCookie("accessToken", ACCESS_TOKEN);
      setCookie("refreshToken", REFRESH_TOKEN);
    }
    NaverLogin();
    navigate.replace("/");
  }, []);

  return null;
}

export default NaverRedirect;
