package kr.co.smart;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.smart.hr.HrService;

@Controller @RequestMapping("/hr")
public class HrController {
	
	@Autowired
	private HrService service;
	
	//사원목록 화면요청
	@RequestMapping("/list")
	public String list(HttpSession session, Model model) {
		session.setAttribute("category", "hr");
		
		//DB에서 사원목록을 조회 후 목록화면에 출력 -> Model 객체에 담기
		model.addAttribute("list",service.employee_list());
		
		return "hr/list";
	}

}
