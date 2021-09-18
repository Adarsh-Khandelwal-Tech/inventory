package hcl.practice.inventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hcl.practice.inventory.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{

	//Optional<Inventory> findByIdAndProductCategory(Integer Id,Integer productCategory);
	
	Inventory findByProductProductName(String productName);
	
	@Query("SELECT i FROM Inventory i WHERE i.id IN (:ids)")
	List<Inventory> findByIds(@Param("ids") List<Integer> ids);
}
