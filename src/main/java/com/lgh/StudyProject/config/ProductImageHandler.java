package com.lgh.StudyProject.config;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class ProductImageHandler {

	public String save(MultipartFile image) throws IOException {
		String fileName = getOriginName(image);
		String filePath = "src/main/resources/static/images/" + fileName;
		
		// 나중에 실제 서버 운용해서 이미지 파일을 저장하게 될 때는 그 파일을 저장하는 로직을 추가해야함.

		image.transferTo(new File(filePath));

		return fileName;
	}

	private String getOriginName(MultipartFile image) {
		return image.getOriginalFilename();
	}

}
