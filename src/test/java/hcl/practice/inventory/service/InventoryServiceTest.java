package hcl.practice.inventory.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import hcl.practice.inventory.entity.Inventory;
import hcl.practice.inventory.entity.Product;
import hcl.practice.inventory.model.ProductDTO;
import hcl.practice.inventory.model.ProductUpdateDTO;
import hcl.practice.inventory.repository.InventoryRepository;

 
@RunWith(MockitoJUnitRunner.class)
public class InventoryServiceTest {

	@Mock
	private InventoryRepository inventoryRepository;
	
	
	private InventoryService inventoryService;
	
	@Before
	public void setup() {
		Inventory inv=new Inventory();
		inv.setId(1);
		inv.setAvailableUnits(5);
		Product product=new Product();
		product.setId(1);
		product.setPrice(45);
		product.setProductName("Test Product");
		inv.setProduct(product);
		inventoryService=new InventoryService(inventoryRepository);
		when(inventoryRepository.save(Mockito.any())).thenReturn(inv);
		when(inventoryRepository.findByProductProductName(Mockito.any())).thenReturn(inv);
	}
	
	@Test
	public void applyTestForSave() {
		ProductDTO dto=new ProductDTO();
		dto.setAvailableUnits(5);
		dto.setPrice(45);
		dto.setProductName("Test Product");
		Inventory inv2=inventoryService.postInventory(dto);
		assertEquals(inv2.getProduct().getProductName(),"Test Product");
	}
	
	@Test
	public void applyTestForGet() {
		Inventory inv2=inventoryService.getInventory("Test Product");
		assertEquals(inv2.getProduct().getProductName(),"Test Product");
	}
	
	@Test
	public void applyTestForUpdate() {
		ProductUpdateDTO dto=new ProductUpdateDTO();
		dto.setOperation("add");
		dto.setProductName("Test Product");
		dto.setUnits(10);
		Inventory inv=inventoryService.populateInventory(dto);
		assertEquals(inv.getAvailableUnits(),new Integer(15));
	}
}
