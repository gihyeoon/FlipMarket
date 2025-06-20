package com.lgh.FlipMarket.config;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class ProductImageHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	public String save(MultipartFile image) throws IOException {
		String baseDir = System.getProperty("user.dir") + "/src/main/resources/static/images/";
		String newFileName = System.currentTimeMillis() + "_" + getOriginName(image);
		String filePath = baseDir + newFileName;

		File uploadDir = new File(baseDir);
		if (!uploadDir.exists()) {
			boolean dirCreated = uploadDir.mkdirs(); // 디렉토리 없으면 생성
			if (!dirCreated) {
				throw new IOException("디렉토리 생성 실패 : " + uploadDir.getAbsolutePath());
			}
		}

		// 나중에 실제 서버 운용해서 이미지 파일을 저장하게 될 때는 그 파일을 저장하는 로직을 추가해야함.
		log.info("상품 이미지 파일 경로: " + filePath);

		image.transferTo(new File(filePath));

		return newFileName;
	}

	private String getOriginName(MultipartFile image) {
		return image.getOriginalFilename();
	}

}
