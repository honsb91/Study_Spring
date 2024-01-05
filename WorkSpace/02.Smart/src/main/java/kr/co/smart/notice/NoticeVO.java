package kr.co.smart.notice;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoticeVO {

	
	private int id , readcnt;
	private String title, content, writer, filepath, filename;
	private Date writedate;
}
