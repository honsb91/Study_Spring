package kr.co.smart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.smart.notice.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService serivce;
	
	// 공지글 목록화면 요청
	@RequestMapping("/list")
	public String list(Model model) {
		//DB에서 조회한 정보를 화면에 출력할 수 있도록 Model객체에 담기
		model.addAttribute("list",serivce.notice_list());  
		return "notice/list";
	}

}
