package hcl.practice.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcl.practice.inventory.entity.User;
import hcl.practice.inventory.model.JwtRequest;
import hcl.practice.inventory.repository.UserRepository;

@Service
public class UserService {

	
	private UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	public User registerUser(JwtRequest userVo) {
		User user=new User();
		user.setUsername(userVo.getUsername());
		user.setPassword(userVo.getPassword());
		return userRepository.save(user);		
	}
	
	public User fetchUser(JwtRequest userVo) {
		return userRepository.findByUsernameAndPassword(userVo.getUsername(),userVo.getPassword()).orElse(null);				
	}
	
	public User loadUserByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);				
	}
}
