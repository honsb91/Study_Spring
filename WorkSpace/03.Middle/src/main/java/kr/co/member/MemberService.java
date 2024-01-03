package kr.co.member;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	@Autowired
	private SqlSession sql;
	
	public MemberVO member_info(String user_id) {
		return sql.selectOne("mb.info", user_id);
	}
	
	public int member_join(MemberVO vo) {
		return sql.insert("mb.join", vo);
	}

}
