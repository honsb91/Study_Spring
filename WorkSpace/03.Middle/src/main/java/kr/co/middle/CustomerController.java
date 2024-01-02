package kr.co.middle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import kr.co.customer.CustomerService;
import kr.co.customer.CustomerVO;

@RestController
@RequestMapping(value = "/customer", produces = "text/plain; charset=utf-8")
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	// 고객목록조회 요청
	@RequestMapping("/list")
	public String list(String query) {
		List<CustomerVO> list = service.customer_list(query);
//		System.out.println(new Gson().toJson(list)); 
		return new Gson().toJson(list);
	}
	
	
	@RequestMapping("/info")
	public String info(int id) {
		CustomerVO vo = service.customer_info(id);
		return new Gson().toJson(vo);
	}
	
	@RequestMapping("/delete")
	public void delete(int id) {
		service.customer_delete(id);
	}
	
	@RequestMapping("/update")
	public void update(String vo) {
		CustomerVO customer = new Gson().fromJson(vo, CustomerVO.class);
		service.customer_update(customer);
	}
	
	@RequestMapping("/insert")
	public void insert(String vo) {
		CustomerVO customer =  new Gson().fromJson(vo, CustomerVO.class);
		service.customer_insert(customer);
	}
}
