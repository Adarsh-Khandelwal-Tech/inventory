package hcl.practice.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hcl.practice.inventory.configuration.JwtTokenUtil;
import hcl.practice.inventory.entity.User;
import hcl.practice.inventory.expection.BusinessLogicException;
import hcl.practice.inventory.model.JwtRequest;
import hcl.practice.inventory.model.JwtResponse;
import hcl.practice.inventory.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping(value="/register")
	public ResponseEntity<User> registerUser(@RequestBody(required=true) JwtRequest userVo){		
		return ResponseEntity.ok(userService.registerUser(userVo));
	}
	
	@PostMapping(value="/authenticate")
	public ResponseEntity<JwtResponse> authenticateUser(@RequestBody(required=true) JwtRequest userVo){		
		
		User existingUser=userService.fetchUser(userVo);
		
		if(existingUser!=null) {
			final String token = jwtTokenUtil.generateToken(existingUser);

			return ResponseEntity.ok(new JwtResponse(token));
		}
		else {
			throw new BusinessLogicException("INVALID CREDENTIALS");
		}
		
	}
}
