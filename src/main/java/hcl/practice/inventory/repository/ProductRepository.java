package hcl.practice.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcl.practice.inventory.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	
}
