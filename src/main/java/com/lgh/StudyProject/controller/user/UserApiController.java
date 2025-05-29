package com.lgh.StudyProject.controller.user;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgh.StudyProject.dto.UserDto;
import com.lgh.StudyProject.entity.User;
import com.lgh.StudyProject.repository.UserRepository;
import com.lgh.StudyProject.service.UserService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserApiController {

	private final UserRepository userRepository;

	private final UserService userService;

	private final BCryptPasswordEncoder passwordEncoder;

	public UserApiController(UserRepository userRepository, UserService userService,
			BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/overlap/emailRegister")
	public Map<String, String> emailRegister(@RequestParam("email") String userEmail) {
		return Map.of("result", userService.countUserByEmail(userEmail) > 0 ? "1" : "0");
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

	@PostMapping("/validateUser")
	public int validateUser(@RequestBody Map<String, String> data) {
		Long userNum = userService.findNumByEmail(data.get("email"));
		UserDto userDto = userService.findByNum(userNum);
		String loginUserPwd = userDto.getPwd();

		if (passwordEncoder.matches(data.get("enteredPwd"), loginUserPwd)) {
			return 1;
		} else {
			return 0;
		}
	}

	@PostMapping("/mypage/editProfile/updateEmail")
	public ResponseEntity<String> updateEmail(@RequestBody Map<String, String> data) {
		try {
			int result = userService.updateEmail(data.get("email"), Long.parseLong(data.get("num")));

			if (result > 0) {
				System.out.println("이메일 업데이트 성공");
			} else {
				System.out.println("유저 정보 없음");
			}

			// 업데이트 이후 로그인 정보 갱신
			UserDto userDto = userService.findByNum(Long.parseLong(data.get("num")));
			User user = userRepository.findByEmail(userDto.getEmail())
					.orElseThrow(() -> new RuntimeException("사용자 정보 없음"));

			user.setEmail(userDto.getEmail());

			Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);

			return ResponseEntity.ok("success");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("updateEmail Fail: " + e.getMessage());
		}
	}

	@PostMapping("/mypage/editProfile/updatePhoneNum")
	public ResponseEntity<String> updatePhoneNum(@RequestBody Map<String, String> data) {
		Long num = Long.parseLong(data.get("num"));
		String phoneNum = data.get("phoneNum");

		try {
			int result = userService.updatePhoneNum(phoneNum, num);

			if (result > 0) {
				System.out.println("휴대폰 번호 업데이트 성공");
			} else {
				System.out.println("유저 정보 없음");
			}
			return ResponseEntity.ok("success");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("updatePhoneNum Fail: " + e.getMessage());
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

			User user = userRepository.findById(num).orElseThrow(() -> new RuntimeException("사용자 없음"));

			user.setPwd(encodedNewPwd);

			Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);

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

			User user = userRepository.findById(num).orElseThrow(() -> new RuntimeException("사용자 없음"));

			user.setPwd(encodedNewPwd);

			Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);

			return Map.of("result", "1");
		}
	}

	@PostMapping("/resetPassword")
	public Map<String, String> countUserBefResetPassword(@RequestBody Map<String, String> data) {
		String email = data.get("email");
		int cnt = userService.countUserByEmail(email);

		if (cnt == 0) {
			return Map.of("result", "0");
		} else {
			return Map.of("result", "1");
		}
	}
	
	@PostMapping("/mypage/editProfile/deleteAccount")
	public Map<String, String> deleteAccount(@RequestBody Map<String, String> data) {
		Long userNum = Long.parseLong(data.get("userNum"));
		
		userService.deleteByNum(userNum);
		
		return Map.of("result", "0");
	}

}
