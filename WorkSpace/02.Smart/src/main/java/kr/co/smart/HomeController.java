package kr.co.smart;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.smart.member.MemberService;
import kr.co.smart.member.MemberVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private MemberService member;
	
	// 주소 자동주입
	@Autowired 
	private BCryptPasswordEncoder pwEncoder;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session, Model model) {
		// Test 하는동안 사용할 수 있도록 임시 로그인처리
		String user_id="sktmdqls", user_pw="1bf0460a-7ded-411c-bb0b-0ee0b9c66b55";
		MemberVO vo = member.member_info(user_id);
		if( pwEncoder.matches(user_pw, vo.getUser_pw()) ) {
			session.setAttribute("loginInfo", vo);
		}
		
		session.removeAttribute("category");
		return "home";
	}
	
	
	
}
