package com.hanul.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import member.MemberVO;

@Controller
public class MemberController {
	
	@RequestMapping("/login_result")
	public String login(String userid, String userpw) {
		// 아이디 , 비번 일치해서 로그인 성공 화면 home화면으로 연결
		//            일치X -> 로그인 실패하면 로그인화면으로 연결
		// 아이디 : ok , 비번 ok 이면 로그인 성공
		if( userid.equals("ok") && userpw.equals("ok")) {
			//return "home"; // forward 방식
			return "redirect:/"; //redirect 방식
		}else {
			//return "member/login"; // forward 방식
			return "redirect:login"; //redirect 방식
		}
	}
	
	@RequestMapping("/login")
	public String login() {
		return "member/login";
	}
	
	@RequestMapping("/joinPath/{name}/{gender}/{e}/{age}")
	public String join(Model model, @PathVariable String name, @PathVariable String gender, 
									@PathVariable("e") String mail,	@PathVariable int age) {
		model.addAttribute("name", name);
		model.addAttribute("gender", gender);
		model.addAttribute("email", mail);
		model.addAttribute("age", age);
		model.addAttribute("method","@PathVariabled방식");
		return "member/info";
	}
	
	
	
	@RequestMapping("/joinData")
	public String join(Model model, MemberVO vo) {
		model.addAttribute("vo", vo);
		model.addAttribute("method", "데이터객체 방식");
		return "member/info";
	}
	
	// 화면을 통해 서브밋된 파라미터를 @RequestParam 접근하기
	@RequestMapping("/joinParam")
	public String join(Model model, int age, @RequestParam("email") String mail, String gender, @RequestParam String name) {
		model.addAttribute("age", age);
		model.addAttribute("email", mail);
		model.addAttribute("gender", gender);
		model.addAttribute("name", name);
		model.addAttribute("method", "@RequestParam 방식");
		return "member/info";
	}
	
	

	// 화면을 통해 서브밋된 파라미터를 HttpServletRequest 접근하기
	@RequestMapping("/joinRequest")
	public String join(HttpServletRequest request, Model model) {
		String name = request.getParameter("name");
		model.addAttribute("name", name);
		model.addAttribute("gender",request.getParameter("gender"));
		model.addAttribute("email",request.getParameter("email"));
		//String -> int -> Integer.parseInt : int
		//String -> int -> Integer.valueOf : Integer
		model.addAttribute("age", Integer.valueOf(request.getParameter("age")));
		model.addAttribute("method", "HttpServletRequest 방식");
		return "member/info";
	}
	
	@RequestMapping("/member")
	public String member() {
		
		
		
		return "member/join";
	}

}
