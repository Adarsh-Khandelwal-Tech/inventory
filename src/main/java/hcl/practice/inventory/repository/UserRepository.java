package hcl.practice.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcl.practice.inventory.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

	Optional<User> findByUsernameAndPassword(String username,String password);
	
	Optional<User> findByUsername(String username);
}
