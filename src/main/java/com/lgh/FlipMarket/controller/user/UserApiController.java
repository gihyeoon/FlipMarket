package com.lgh.FlipMarket.controller.user;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.lgh.FlipMarket.config.AuthenticationUserId;
import com.lgh.FlipMarket.config.Constants;
import com.lgh.FlipMarket.config.RandomPasswordGenerator;
import com.lgh.FlipMarket.config.mail.MailService;
import com.lgh.FlipMarket.dto.UserDto;
import com.lgh.FlipMarket.entity.User;
import com.lgh.FlipMarket.repository.UserRepository;
import com.lgh.FlipMarket.service.AddressService;
import com.lgh.FlipMarket.service.ReviewService;
import com.lgh.FlipMarket.service.UserService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserApiController {

	private final UserRepository userRepository;

	private final UserService userService;

	private final AddressService addressService;

	private final ReviewService reviewService;

	private final BCryptPasswordEncoder passwordEncoder;

	private final MailService mailService;

	private final AuthenticationUserId authenticationUserId;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	public UserApiController(UserRepository userRepository, UserService userService, AddressService addressService,
			ReviewService reviewService, BCryptPasswordEncoder passwordEncoder, MailService mailService,
			AuthenticationUserId authenticationUserId) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.addressService = addressService;
		this.reviewService = reviewService;
		this.passwordEncoder = passwordEncoder;
		this.mailService = mailService;
		this.authenticationUserId = authenticationUserId;
	}

	@GetMapping("/overlap/emailRegister")
	public Map<String, String> emailRegister(@RequestParam("email") String userEmail) {
		return Map.of(Constants.RETURN_KEY_NAME, userService.countUserByEmail(userEmail) > 0 ? "1" : "0");
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Map<String, String> data) {
		UserDto userDto = UserDto.builder().name(data.get("name")).email(data.get("email"))
				.pwd(passwordEncoder.encode(data.get("pwd"))).build();
		try {
			// 사용자 등록
			userService.register(userDto);

			// 사용자 주소지 등록
			String zonecode = data.get("zonecode");
			String address = data.get("address");
			String detailAddress = data.get("detailAddress");
			String userSelectedType = data.get("userSelectedType");
			String jibunAddress = data.get("jibunAddress");
			String buildingName = data.get("buildingName");

			addressService.addAddress(zonecode, address, jibunAddress, detailAddress, buildingName, userSelectedType,
					true, userDto);

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
				log.info("이메일 업데이트 성공");
			} else {
				log.warn("유저 정보 없음");
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
				log.info("휴대폰 번호 업데이트 성공");
			} else {
				log.warn("유저 정보 없음");
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
				log.info("비밀번호 변경 완료");
			} else {
				log.warn("비밀번호 변경할 유저 정보 없음");
			}

			User user = userRepository.findById(num).orElseThrow(() -> new RuntimeException("사용자 없음"));

			user.setPwd(encodedNewPwd);

			Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);

			return Map.of(Constants.RETURN_KEY_NAME, "1");
		} else {
			return Map.of(Constants.RETURN_KEY_NAME, "0");
		}
	}

	@Deprecated
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
				log.info("비밀번호 재설정 완료");
			} else {
				log.warn("비밀번호 재설정 실패");
			}

			User user = userRepository.findById(num).orElseThrow(() -> new RuntimeException("사용자 없음"));

			user.setPwd(encodedNewPwd);

			Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);

			return Map.of("result", "1");
		}
	}

	@PostMapping("/updateTempPassword")
	public Map<String, String> updateTempPassword(@RequestBody Map<String, String> data) {
		String email = data.get("email");
		int cnt = userService.countUserByEmail(email);

		if (cnt == 0) {
			return Map.of(Constants.RETURN_KEY_NAME, "0");
		} else {
			// 소셜 계정은 비밀번호 개념이 없기 때문에 재설정이 불가능하게 한다.
			Long userNum = userService.findNumByEmail(email);
			String provider = userService.findByNum(userNum).getProvider();

			if (provider != null) {
				return Map.of(Constants.RETURN_KEY_NAME, "2", "provider", provider);
			}

			// 임시 비밀번호를 해당 메일로 보내줌.
			String tempPassword = RandomPasswordGenerator.generateRandomString(16);

			mailService.sendTempPassword(email, "임시 비밀번호입니다.", tempPassword);

			// 그리고 실제 DB에도 해당 임시 비밀번호를 UPDATE
			userService.updatePwd(passwordEncoder.encode(tempPassword), userNum);

			return Map.of(Constants.RETURN_KEY_NAME, "1");
		}
	}

	@PostMapping("/mypage/editProfile/deleteAccount")
	public Map<String, String> deleteAccount(@RequestBody Map<String, String> data) {
		Long userNum = Long.parseLong(data.get("userNum"));

		userService.deleteByNum(userNum);

		return Map.of(Constants.RETURN_KEY_NAME, "0");
	}

	@PostMapping("/review/addReview")
	public Map<String, String> addReview(@RequestBody Map<String, String> data) {
		Long userNum = authenticationUserId.getUserNum();
		Long productNum = Long.parseLong(data.get("productNum"));
		int rating = Integer.parseInt(data.get("rating"));
		String description = data.get("description");

		reviewService.addReview(userNum, productNum, rating, description);

		return Map.of(Constants.RETURN_KEY_NAME, "0");
	}

}
