package kr.co.smart.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.smart.member.MemberVO;

@Service
public class CommonUtility {
	
	private String EMAIL = "honsb@naver.com";
	
	private void connectMailServer(HtmlEmail mail) {
		mail.setDebug(true);
		mail.setCharset("utf-8");
		
		mail.setHostName("smtp.naver.com");
		mail.setAuthentication(EMAIL, "gksdnfwlrdjq!"); // 관리자의 이메일 주소, 해당 이메일의 비번
		mail.setSSLOnConnect(true); // 로그인버튼 클릭
	}
	
	
	// 임시 비밀번호를 이메일로 보내기
	public boolean sendPassword(MemberVO vo, String pw) {
		boolean send = true;
		
		HtmlEmail mail = new HtmlEmail();
		connectMailServer(mail); // 메일서버에 연결하기 (메일 아이디/비번 입력 후 로그인)
		
		try {
			mail.setFrom(EMAIL,"스마트 IoT 관리자"); // 메일 보내는이 정보
			mail.addTo(vo.getEmail(), vo.getName());// 메일 수신인 정보
			
			// 메일 제목
			mail.setSubject("스마트 IoT 로그인 임시비밀번호 발급");
			// 메일 내용
			StringBuffer content = new StringBuffer();
			content.append("<h3>").append(vo.getName()) .append("임시 비밀번호가 발급되었습니다.</h3>");
			content.append("<div>아이디:").append(vo.getUser_id()).append("</div>");
			content.append("<div>임시 비밀번호: ").append(pw).append("</strong> </div>");
			content.append("<div>발급된 임시 비밀번호로 로그인 후 비밀번호를 변경하세요</div>");
			mail.setHtmlMsg(content.toString());
			
			mail.send(); // 메일 보내기 버튼 클릭
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			send = false;
		}
		
		return send;
	}
	

	// 웹브라우저 요청의 루트반환
	public String appURL(HttpServletRequest request) {
		StringBuffer url = new StringBuffer("http://");
		url.append(request.getServerName()).append(":"); // http://localhost:, http://127.0.0.1:
		url.append(request.getServerPort());			 // http://localhost:80, http://127.0.0.1:8080
		url.append(request.getContextPath());			 // http://localhost:80/smart, http://127.0.0.1:8080/web
		return url.toString();
	}
	
	public String requestAPI(String apiURL) {
	    try {
	        URL url = new URL(apiURL);
	        HttpURLConnection con = (HttpURLConnection)url.openConnection();
	        con.setRequestMethod("GET");
	        int responseCode = con.getResponseCode();
	        BufferedReader br;
	        if (responseCode == 200) { // 정상 호출
	          br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
	        } else {  // 에러 발생
	          br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
	        }
	        String inputLine;
	        StringBuilder res = new StringBuilder();
	        while ((inputLine = br.readLine()) != null) {
	          res.append(inputLine);
	        }
	        br.close();
	        if (responseCode == 200) {
	        	apiURL = res.toString();
	           System.out.println(res.toString());
	        }
	      } catch (Exception e) {
	        // Exception 로깅
	      }
	    return apiURL;
	}
	
	public String requestAPI(String apiURL, String property) {
	    try {
	        URL url = new URL(apiURL);
	        HttpURLConnection con = (HttpURLConnection)url.openConnection();
	        con.setRequestMethod("GET");
	        con.setRequestProperty("Authorization", property);
	        
	        int responseCode = con.getResponseCode();
	        BufferedReader br;
	        if (responseCode == 200) { // 정상 호출
	          br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
	        } else {  // 에러 발생
	          br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
	        }
	        String inputLine;
	        StringBuilder res = new StringBuilder();
	        while ((inputLine = br.readLine()) != null) {
	          res.append(inputLine);
	        }
	        br.close();
	        if (responseCode == 200) {
	        	apiURL = res.toString();
	           System.out.println(res.toString());
	        }
	      } catch (Exception e) {
	        // Exception 로깅
	      }
	    return apiURL;
	}
	
	// 파일업로드
	public String fileUpload(String category, MultipartFile file, HttpServletRequest request) {
		// d://app/upload/profile/2024/01/05/abc.png
		String upload = "d://app/upload/" + category + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) ;
		File dir = new File(upload);
		if(! dir.exists() ) dir.mkdirs();
		
		String filename = UUID.randomUUID().toString() + "."
						+StringUtils.getFilenameExtension(file.getOriginalFilename());
		
		try {
			file.transferTo(new File(upload, filename));
		}catch(Exception e) {
			
		}
		
		
		return upload.replace("d://app/upload", fileURL(request)) + "/" + filename;
	}
	
	// 파일서비스 받을 URL
		public String fileURL(HttpServletRequest request) {
			StringBuffer url = new StringBuffer("http://");
			url.append(request.getServerName()).append(":"); // http://localhost:, http://127.0.0.1:
			url.append(request.getServerPort());			 // http://localhost:80, http://127.0.0.1:8080
			url.append("/file");			 // http://localhost:80/smart, http://127.0.0.1:8080/web
			return url.toString();
		}
		
		// 회원가입 축하메일 보내기
		public void sendWelcome(MemberVO vo, String welcomeFile) {
			HtmlEmail mail = new HtmlEmail();
			connectMailServer(mail);
			
			try {
				mail.setFrom(EMAIL);
				mail.addTo(vo.getEmail(), vo.getName());
				
				mail.setSubject("한울 스마트 IoT 융합 과정 가입");
				
				StringBuffer msg = new StringBuffer();
				msg.append("<body>");
				msg.append("<h3>스마트 IoT 융합 과정</h3>");
				msg.append("<div>과정 가입을 축하합니다.</div>");
				msg.append("<div>프로젝트까지 마무리하시고 취업에 성공하시길 바랍니다.</div>");
				msg.append("<div>첨부된 파일을 확인하신 후 등교하세요.</div>");
				msg.append("</body>");
				mail.setHtmlMsg(msg.toString());
				
				// 파일첨부하기
				EmailAttachment file = new EmailAttachment();
				file.setPath(welcomeFile);
				mail.attach(file);
				
				mail.send();
				
			}catch(Exception e) {
				
			}
		}
}
