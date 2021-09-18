package hcl.practice.inventory.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import hcl.practice.inventory.entity.Inventory;
import hcl.practice.inventory.model.ProductDTO;
import hcl.practice.inventory.model.ProductUpdateDTO;
import hcl.practice.inventory.service.InventoryService;

@RestController
public class InventoryController {
	Logger log=LogManager.getLogger(InventoryController.class);
	@Autowired
	private InventoryService inventoryService;
	
	
	@PostMapping(value="/inventory")
	public ResponseEntity<Inventory> postInventory(@RequestBody ProductDTO productDTO,@RequestHeader(required=true) Integer userId,@RequestHeader(required=true) String password
			){
		
		log.debug("Controller : Method: postInventory : start");
		log.debug("Controller : API-> Post Inventory :/inventory ");
		log.debug("Controller : Request Payload "+productDTO);
		Inventory inv=inventoryService.postInventory(productDTO,userId,password);
		log.debug("Controller : Method: postInventory : end");
		return ResponseEntity.ok(inv);
	}
	
	@PostMapping(value="/bulk/inventory")
	public ResponseEntity<List<Inventory>> bulkPostInventory(@RequestBody List<ProductDTO> productDTOList){
		List<Inventory> inv=inventoryService.bulkPostInventory(productDTOList);
		return ResponseEntity.ok(inv);
	}
	
	@GetMapping(value="/inventory/{productName}")
	public ResponseEntity<Inventory> getInventory(@PathVariable("productName") String productName){
		Inventory inv=inventoryService.getInventory(productName);
		return ResponseEntity.ok(inv);
	}
	
	@GetMapping(value="/bulk/inventory/")
	public ResponseEntity<List<Inventory> > getAllInventory(){
		List<Inventory> inv=inventoryService.getInventory();
		return ResponseEntity.ok(inv);
	}
	
	@PutMapping(value="/inventory")
	public ResponseEntity<Inventory> updateInventory(@RequestBody ProductUpdateDTO productUpdateDTO){
		Inventory inv=inventoryService.UpdateInventory(productUpdateDTO);
		return ResponseEntity.ok(inv);
	}
	
	@DeleteMapping(value="/inventory/{productName}")
	public ResponseEntity<String> DeleteInventory(@PathVariable("productName") String productName){
		String response=inventoryService.deleteInventory(productName);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value="/bulkDelete")
	public ResponseEntity<String> DeleteAllInventory(@RequestBody List<Integer> ids){
		String response=inventoryService.deleteInventory(ids);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping(value="/bulk/inventory")
	public ResponseEntity<List<Inventory>> updateInventory(@RequestBody List<ProductUpdateDTO> productUpdateDTO){
		List<Inventory> inv=inventoryService.bulkUpdateInventory(productUpdateDTO);
		return ResponseEntity.ok(inv);
	}

}
