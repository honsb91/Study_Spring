package kr.co.smart;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.smart.customer.CustomerServiceImpl;
import kr.co.smart.customer.CustomerVO;

@Controller
public class CustomerController {

	@Autowired
	private CustomerServiceImpl service;
	
	//고객정보화면 요청
	@RequestMapping("/info.cu")
	public String info(Model model, int id) {
		//선택한 고객정보를 DB에서 조회 후 정보화면에 출력
		//조회해온 정보를 화면에 출력할 수 있도록 Model 객체에 담기
		CustomerVO vo =	service.customer_info(id);
		model.addAttribute("vo",vo);
		return "customer/info";
	}
	
	
	// 고객목록화면 요청
	@RequestMapping("/list.cu")
	public String list(Model model, HttpSession session) {
		session.setAttribute("category", "cu");
		//DB에서 고객목록을 조회 후 화면에 출력
		List<CustomerVO> list = service.customer_list();
		//조회한 정보를 화면에 출력할 수 있도록 Model객체에 담기
		model.addAttribute("list",list);
		return "customer/list";
	}
	
	
}
