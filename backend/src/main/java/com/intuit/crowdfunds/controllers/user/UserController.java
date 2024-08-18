package com.intuit.crowdfunds.controllers.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.crowdfunds.dtos.UserDTO;
import com.intuit.crowdfunds.services.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<UserDTO> getUserInfo() {
		UserDTO dto = userService.getUserDetailsFromContext();
		return ResponseEntity.ok(dto);
	}

}
