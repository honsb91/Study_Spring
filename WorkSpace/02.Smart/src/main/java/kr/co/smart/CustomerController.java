package kr.co.smart;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String list(Model model, HttpSession session, @RequestParam(defaultValue = "") String name) {
		session.setAttribute("category", "cu");
		//DB에서 고객목록을 조회 후 화면에 출력
		//List<CustomerVO> list = name.isEmpty() ? service.customer_list() : service.customer_list(name);
		List<CustomerVO> list = service.customer_list(name);
		//List<CustomerVO> list = name == null ? service.customer_list() : service.customer_list(name);
		//조회한 정보를 화면에 출력할 수 있도록 Model객체에 담기
		model.addAttribute("list",list);
		model.addAttribute("name",name);
		return "customer/list";
	}
	
	/*
	 * public String list(Model model, String name ,HttpSession session) {
	 * session.setAttribute("category", "cu"); //DB에서 고객목록을 조회 후 화면에 출력
	 * //List<CustomerVO> list = name.isEmpty() ? service.customer_list() :
	 * service.customer_list(name); //List<CustomerVO> list =
	 * service.customer_list(name); List<CustomerVO> list = name == null ?
	 * service.customer_list() : service.customer_list(name); //조회한 정보를 화면에 출력할 수
	 * 있도록 Model객체에 담기 model.addAttribute("list",list); return "customer/list"; }
	 */
	
	// 고객정보 수정화면 요청
	@RequestMapping("/modify.cu")
	public String modify(int id , Model model) {
		//화면에서 선택한 고객정보를 DB에서 조회 후 수정화면에 출력 -> Model 객체에 담기
		model.addAttribute("vo",service.customer_info(id));
		return "customer/modify";
	}
	
	// 고객정보 수정처리 요청
	@RequestMapping("/update.cu")
	public String update(CustomerVO vo) {
		// 화면에서 변경입력한 정보로 DB에 변경저장 
		service.customer_update(vo);
		// 화면연결->고객정보화면으로 응답
		return "redirect:info.cu?id=" + vo.getCustomer_id();
	}
		//DML처리를 한 후 화면연결은 redirect
	
	// 고객정보 삭제 처리
	@RequestMapping("/delete.cu")
	public String delete(int id) {
		//선택한 고객정보를 DB에서 삭제 처리 -> 목록화면으로 연결
		service.customer_delete(id);
		return "redirect:list.cu";
	}
	
	// 신규 고객정보 등록화면 요청
	@RequestMapping("/register.cu")
	public String register() {
		
		return "customer/register";
	}
	
	// 신규 고개정보 저장처리
	@RequestMapping("/insert.cu")
	public String insert(CustomerVO vo) {
		// 화면에서 입력한 정보로 DB에 신규저장 -> 목록화면으로 연결
		service.customer_register(vo);
		return "redirect:list.cu";
	}
	
	
}
