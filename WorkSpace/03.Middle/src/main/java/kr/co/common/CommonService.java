package kr.co.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommonService {

	public String fileupload(String category, MultipartFile file, HttpServletRequest request) {
		// 물리적 저장 : d://app/upload/profile/2024/01/03/abc.jpg
		// http://localhost:80/file/profile/2024/01/03/abc.jpg
		// http://localhost:80/file/board/2024/01/03/abc.jpg
		
		String upload ="d://app/upload/" + category + 
						new SimpleDateFormat("/yyyy/MM/dd/").format( new Date());
		
		// 폴더가 있는지 확인 후 없으면 폴더만들기
		File folder = new File (upload); 
		if ( ! folder.exists()) folder.mkdirs();
		// 업로드하는 파일명을 고유한 아이디로 저장
		String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
		String filename = UUID.randomUUID().toString() + "." + ext;
		
		
		try {
			file.transferTo(new File(upload, filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return upload.replace("d://app/upload", fileURL(request)) + filename;
	}

	
	public String fileURL(HttpServletRequest request) {
		StringBuffer url = new StringBuffer("http://");
		url .append(request.getServerName()).append( " : ") 		// http://localhost:
			.append(request.getServerPort()).append( "/file");		// 
		
		return url.toString();
	}
}
