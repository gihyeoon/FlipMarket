package com.lgh.StudyProject.config;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class ProductImageHandler {

	public String save(MultipartFile image) throws IOException {
		String fileName = getOriginName(image);
		String filePath = "C:\\productImage\\" + fileName; // 이미지를 저장할 경로를 지정. 이 때 주의해야할 점은 절대 경로를 사용해야한다. (실제 서버를 운영하게 되면 그 서버의 이미지 파일을 저장하는 폴더의 절대 경로로 수정)
		
		image.transferTo(new File(filePath));
		
		return filePath;
	}
	
	private String getOriginName(MultipartFile image) {
		return image.getOriginalFilename();
	}
	
}
