package kr.co.smart.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberVO {
	
	private String user_id, user_pw, name, email, birth, phone, 
				   address, post, social, role, gender, profile;

}
