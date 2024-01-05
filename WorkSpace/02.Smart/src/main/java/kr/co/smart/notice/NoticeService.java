package kr.co.smart.notice;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
	
	@Autowired
	@Qualifier("hanul")
	private SqlSession sql;

	// 신규 공지글저장
	public int notice_register(NoticeVO vo) {
		return 0;
	}
	
	// 공지글 목록 조회
	public List<NoticeVO> notice_list(){
		return sql.selectList("notice.list");
	}
	
	// 공지글 정보 조회
	public NoticeVO notice_info(int id) {
		return null;
	}
	
	// 공지글 정보 변경저장
	public int notice_update(NoticeVO vo) {
		return 0;
	}
	
	// 공지글 정보 삭제
	public int notice_delete(int id) {
		return 0;
	}
}
