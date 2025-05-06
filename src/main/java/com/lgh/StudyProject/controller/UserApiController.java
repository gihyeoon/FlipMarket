package com.lgh.StudyProject.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgh.StudyProject.dto.UserDto;
import com.lgh.StudyProject.service.UserService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserApiController {

	private final UserService userService;

	private final BCryptPasswordEncoder passwordEncoder;

	public UserApiController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/overlap/idRegister")
	public Map<String, String> idRegister(@RequestParam(value = "id") String id) {
		return Map.of("result", userService.checkDuplicateId(id) ? "1" : "0");
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserDto userDto) {
		String encodedPwd = passwordEncoder.encode(userDto.getPwd());
		userDto.setPwd(encodedPwd);
		try {
			userService.register(userDto);
			return ResponseEntity.ok("success");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Register Fail: " + e.getMessage());
		}
	}

	@PostMapping("/mypage/editProfile/updateId")
	public ResponseEntity<String> updateId(@RequestBody Map<String, String> data) {
		try {
			int result = userService.updateId(data.get("id"), Long.parseLong(data.get("num")));

			if (result > 0) {
				System.out.println("아이디 업데이트 성공");
			} else {
				System.out.println("유저 정보 없음");
			}
			return ResponseEntity.ok("success");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("updateId Fail: " + e.getMessage());
		}
	}

	@PostMapping("/mypage/editProfile/updatePhoneNum")
	public ResponseEntity<String> updatePhoneNum(@RequestBody Map<String, String> data) {
		try {
			int result = userService.updatePhoneNum(data.get("phoneNum"), Long.parseLong(data.get("num")));

			if (result > 0) {
				System.out.println("휴대폰 번호 업데이트 성공");
			} else {
				System.out.println("유저 정보 없음");
			}
			return ResponseEntity.ok("success");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("updateId Fail: " + e.getMessage());
		}
	}

	@PostMapping("/mypage/editProfile/updatePwd")
	public Map<String, String> updatePwd(@RequestBody Map<String, String> data) {
		Long num = Long.parseLong(data.get("num"));
		String pwd = data.get("pwd");
		String newPwd = data.get("newPwd");

		String originPwd = userService.selectUserPwdByNum(num);
		String encodedNewPwd = passwordEncoder.encode(newPwd);

		if (passwordEncoder.matches(pwd, originPwd)) {
			int result = userService.updatePwd(encodedNewPwd, num);

			if (result > 0) {
				System.out.println("비밀번호 변경 완료");
			} else {
				System.out.println("비밀번호 변경할 유저 정보 없음");
			}

			return Map.of("result", "1");
		} else {
			return Map.of("result", "0");
		}
	}

	@PostMapping("/resetPassword/updatePwd")
	public Map<String, String> resetPassword(@RequestBody Map<String, String> data) {
		Long num = Long.parseLong(data.get("num"));

		String newPwd = data.get("newPwd");
		String originPwd = userService.selectUserPwdByNum(num);

		if (passwordEncoder.matches(newPwd, originPwd)) {
			return Map.of("result", "0");
		} else {
			String encodedNewPwd = passwordEncoder.encode(newPwd);

			int result = userService.updatePwd(encodedNewPwd, num);

			if (result > 0) {
				System.out.println("비밀번호 재설정 완료");
			} else {
				System.out.println("비밀번호 재설정 실패");
			}

			return Map.of("result", "1");
		}
	}

	@PostMapping("/resetPassword")
	public Map<String, String> countUserBefResetPassword(@RequestBody Map<String, String> data) {
		String id = data.get("id");
		int cnt = userService.countUserById(id);

		if (cnt == 0) {
			return Map.of("result", "0");
		} else {
			return Map.of("result", "1");
		}

	}

}
