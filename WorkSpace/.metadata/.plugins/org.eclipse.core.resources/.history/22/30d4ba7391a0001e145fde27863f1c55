package kr.co.smart;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller @RequestMapping("/hr")
public class HrController {
	
	//사원목록 화면요청
	@RequestMapping("/list")
	public String list(HttpSession session) {
		session.setAttribute("category", "hr");
		return "hr/list";
	}

}
