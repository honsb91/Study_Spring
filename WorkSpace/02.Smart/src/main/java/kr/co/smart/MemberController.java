package kr.co.smart;

import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.co.smart.common.CommonUtility;
import kr.co.smart.member.MemberService;
import kr.co.smart.member.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private CommonUtility common;
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private BCryptPasswordEncoder pwEncoder;
	
	// 로그인 화면 요청
	@RequestMapping("/login")
	public String login(HttpSession session) {
		session.setAttribute("category", "login");
		return "default/member/login";
	}
	
	// 로그인 처리 요청
	@ResponseBody
	@RequestMapping(value = "/smartLogin", produces = "text/html; charset=utf-8")
	public String login(HttpServletRequest request, String user_id, String user_pw, HttpSession session) {
		MemberVO vo = service.member_info(user_id);
		StringBuffer msg = new StringBuffer("<script>");
		boolean match = false;
		if (vo != null) {
			match = pwEncoder.matches(user_pw, vo.getUser_pw());
		}
		if (match) {
			session.setAttribute("loginInfo", vo);
			msg.append("location='").append(common.appURL(request)).append("'");
		}
		else {
			msg.append("alert('아이디나 비밀번호가 일치하지 않습니다.'); history.go(-1)");

		}
		msg.append("</script>");
		return msg.toString();
	}
	
	private final String KAKAO_CLIENT_ID = "b62856b0ff9bfd66e896f9a21a41a656";
	
	private final String NAVER_CLIENT_ID = "u92nMoIhNVfUiT4JLabv";
	private final String NAVER_SECRET = "5hcnLCtfHR";
	
	// 네이버 로그인요청
	@RequestMapping("/naverLogin")
	public String naverlogin(HttpSession session, HttpServletRequest request) {
		// 네이버 로그인 연동 URL 생성하기
		// https://nid.naver.com/oauth2.0/authorize?response_type=code -> 다 똑같음
		// &client_id="u92nMoIhNVfUiT4JLabv"
		// &state=STATE_STRING
		// &redirect_uri=CALLBACK_URL
		String state = UUID.randomUUID().toString();
		session.setAttribute("state", state);
		
		StringBuffer url = new StringBuffer("https://nid.naver.com/oauth2.0/authorize?response_type=code");
		url.append("&client_id=").append(NAVER_CLIENT_ID);
		url.append("&state=").append(state);
		url.append("&redirect_uri=").append(common.appURL(request)).append("/member/naverCallback"); //http://localhost:80/smart/member/naverCallback
		return "redirect:" + url.toString();
	}
	
	@RequestMapping("/naverCallback")
	public String naverCallback(String state, String code , HttpSession session) {
		if ( code !=null) return "redirect:/";
		
		/*
		 * if( code !=null ) {
		 * //https://nid.naver.com/oauth2.0/token?grant_type=authorization_code
		 * &client_id=jyvqXeaVOVmV
		 * &client_secret=527300A0_COq1_XV33cf
		 * &code=EIc5bFrl4RibFls1
		 * &state=9kgsGTfH4j7IyAkg
		 * 
		 * }
		 */
		
		StringBuffer url = new StringBuffer("https://nid.naver.com/oauth2.0/token?grant_type=authorization_code");
		url.append("&client_id=").append(NAVER_CLIENT_ID);
		url.append("&client_secret=").append(NAVER_SECRET);
		url.append("&code=").append(code);
		url.append("&state=").append(state);
		String response = common.requestAPI(url.toString());
		
		HashMap<String, String> map = new Gson().fromJson(response, new TypeToken<HashMap<String, String>>(){}.getType() );
		String token = map.get("access_token");
		String type = map.get("token_type");
		
		/*
		 * { "access_token":
		 * "AAAAQosjWDJieBiQZc3to9YQp6HDLvrmyKC+6+iZ3gq7qrkqf50ljZC+Lgoqrg",
		 * "refresh_token":
		 * "c8ceMEJisO4Se7uGisHoX0f5JEii7JnipglQipkOn5Zp3tyP7dHQoP0zNKHUq2gY",
		 * "token_type": "bearer", "expires_in": "3600" }
		 */
		
		response = common.requestAPI("https://openapi.naver.com/v1/nid/me", type + " " + token);
		
		JSONObject json = new JSONObject( response );
		
		
		//HashMap<String, Object> me = new Gson().fromJson(response, new TypeToken<HashMap<String, Object>>(){}.getType() );
		if(json.getString("resultcode").equals("00")) {
			
			json = json.getJSONObject("response");
			
			 String email = json.getString("email");
			 String gender = json.getString("gender").equals("F") ? "여" : "남";
			 String id = json.getString("id");
			 String name = json.getString("name");
			 String profile = json.getString("profile");
			 String phone = json.getString("mobile");
			 
			 MemberVO member = new MemberVO();
			 member.setSocial("N"); // N : 네이버 , K : 카카오
			 member.setUser_id(id);
			 member.setName(name);
			 member.setEmail(email);
			 member.setGender(gender);
			 member.setProfile(profile);
			 member.setPhone(json.getString("mobile"));
			 
			 // 네이버 로그인이 처음인 경우 : insert, 아니면 update
			 if(service.member_info(id) == null ) {
				 service.member_join(member);
			 }else {
				 service.member_update(member);
			 }
			 session.setAttribute("loginInfo", member);
		}
		
//		{
//			  "resultcode": "00",
//			  "message": "success",
//			  "response": {
//			    "email": "openapi@naver.com",
//			    "nickname": "OpenAPI",
//			    "profile_image": "https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif",
//			    "age": "40-49",
//			    "gender": "F",
//			    "id": "32742776",
//			    "name": "오픈 API",
//			    "birthday": "10-01"
//			  }
//			}
		
		return "redirect:/";
	}
	
	// 로그아웃 처리 요청
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		//세션에 있는 로그인정보 삭제 후 웰컴페이지로 연결
		session.removeAttribute("loginInfo");
		return "redirect:/";
	}
	
	// 비밀번호 찾기 화면 요청
	@RequestMapping("/findPassword")
	public String findPassword(HttpSession session) {
		session.setAttribute("category", "find");
		return "default/member/find";
	}
	
	// 임시비밀번호 발급처리 요청
	@ResponseBody @RequestMapping(value="/resetPassword", produces="text/html; charset=utf-8")
	public String resetPassword(MemberVO vo) {
		
		vo = service.member_userid_email(vo);
		
		StringBuffer msg = new StringBuffer("<script>");
		
		if(vo == null) {
			msg.append("alert('아이디나 이메일이 맞지 않습니다. \\n확인하세요!'); ");
			msg.append("location='findPassword'");
		}else {
			// 화면에서 입력한 아이디와 이메일이 일치하는 회원에게 임시 비밀번호를 발급해 이메일로 보내기
			// 임시비밀번호를 발급해 이메일로 보내기
			String pw = UUID.randomUUID().toString(); //adsfads213_asdas435ad_adf321adf
			pw = pw.substring( pw.lastIndexOf("_")+1);	// adf321adf
			vo.setUser_pw(pwEncoder.encode(pw));
			
			if(	service.member_resetPassword(vo)==1 && common.sendPassword(vo, pw)) {
				msg.append("alert('임시 비밀번호가 발급되었습니다. \\n이메일을 확인하세요.'); ");
				msg.append("location='login' ");
			}else {
				msg.append("alert('임시 비밀번호 발급을 실패하였습니다.'); ");
				msg.append("history.go(-1) ");
			}
		}
		
		msg.append("</script>");
		return msg.toString();
	}
	
	// 비밀번호 변경
	@RequestMapping("/changePassword")
	public String changePassword() {
		return "member/password";
	}
	
	// 현재 비밀번호 확인처리 요청
	@RequestMapping("/confirmPassword")
	@ResponseBody
	public int confirmPassword(String user_pw, HttpSession session) {
		// 세션의 로그인정보 꺼내기
		MemberVO vo = (MemberVO)session.getAttribute("loginInfo");
		if( vo == null) {
			// 로그인이 안 되어 있다면 로그인 화면으로 연결되도록
			return -1;
		}else {
			// 입력한 현재 비번이 DB의 비번과 일치하는지
			return pwEncoder.matches(user_pw, vo.getUser_pw()) ? 0 : 1;
		}
	}
	
	// 새 비밀번호로 변경저장 처리요청
	@RequestMapping("/updatePassword")
	@ResponseBody
	public boolean updatePassword(String user_pw, HttpSession session) {
		// 세션의 로그인 정보 꺼내기
		MemberVO vo = (MemberVO)session.getAttribute("loginInfo");
		// 입력 새 비번을 암호화하기
		vo.setUser_pw(pwEncoder.encode(user_pw));
		
		return service.member_resetPassword(vo) == 1 ? true : false;
	}
}
