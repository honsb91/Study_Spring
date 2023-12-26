package kr.co.smart.hr;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class DepartmentVO {
	
	private int department_id, manager_id, location_id;
	private String department_name;

}
