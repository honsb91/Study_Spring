package kr.co.smart.member;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	@Autowired @Qualifier("hanul")
	private SqlSession sql;
	
	// CRUD
	// 회원가입시 회원정보 신규삽입저장
	public int member_join(MemberVO vo) {
		return 0;
	}
	
	// 회원목록조회(관리자)
	public List<MemberVO> member_list() {
		return null;
	}
	
	// 회원정보조회
	public MemberVO member_info(String user_id) {
		return sql.selectOne("member.info", user_id);
	}
	
	// 회원정보 변경저장(마이페이지)
	public int member_update(MemberVO vo) {
		return 0;
	}
	
	// 회원탈퇴 시 회원정보삭제
	public int member_delete(String user_id) {
		return 0;
	}
}
