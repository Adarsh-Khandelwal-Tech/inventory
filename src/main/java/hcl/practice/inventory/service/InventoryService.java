package hcl.practice.inventory.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import hcl.practice.inventory.entity.Inventory;
import hcl.practice.inventory.entity.Product;
import hcl.practice.inventory.entity.ProductTypesEnums;
import hcl.practice.inventory.expection.BusinessLogicException;
import hcl.practice.inventory.model.ProductDTO;
import hcl.practice.inventory.model.ProductUpdateDTO;
import hcl.practice.inventory.repository.InventoryRepository;
import hcl.practice.inventory.repository.ProductRepository;

@Service
public class InventoryService {

	Logger log=LogManager.getLogger(InventoryService.class);
	
	private InventoryRepository inventoryRepository;
	private RestTemplate restTemplate;
	private static final Integer USER_ID=1;
	private static final String PASSWORD="password";
	
	@Autowired
	public InventoryService(InventoryRepository inventoryRepository,RestTemplate restTemplate) {
		this.inventoryRepository=inventoryRepository;
		this.restTemplate=restTemplate;
	}
	
	@HystrixCommand(fallbackMethod = "validateInventory", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "500000")})
	public Inventory postInventory(ProductDTO productDTO,Integer userId, String password) {
		log.debug("Service : Method: postInventory start");
		validateInventory(productDTO,userId,password);
		Map<String,Object> map = restTemplate.exchange("http://purchase-order/purchaseOrder/inv/"+productDTO.getProductName(),
                HttpMethod.GET, null, new ParameterizedTypeReference<Map<String,Object>>() {}).getBody();
		Integer totalUnits=(Integer)map.get("totalUnits");
		System.out.println("Total Units "+totalUnits);
		if(totalUnits >=1000) {
			Map<String,Object> requestMap=new HashMap<>();
			requestMap.put("productName",productDTO.getProductName());
			requestMap.put("totalUnits",productDTO.getAvailableUnits());
			requestMap.put("operation", "delete");
			HttpEntity<Map<String,Object>> requestEntity=null;
			requestEntity=new HttpEntity<Map<String,Object>>(requestMap);
			Inventory inv=populatePostInventory(productDTO);
			
			restTemplate.exchange("http://purchase-order/purchaseOrder/inv/",
	                HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<Map<String,Object>>() {});
			log.debug("Service : Method: postInventory end");
			return inventoryRepository.save(inv);	
		}
		else {
			Inventory inv=new Inventory();
			log.debug("Service : Method: postInventory end");
			return inv;
		}
		
		
	}
		
	private Inventory validateInventory(ProductDTO productDTO,Integer userId, String password) {
		log.debug("Service : Method: validateInventory start");
		String productName=productDTO.getProductName();
		if(!userId.equals(USER_ID) || !password.equals(PASSWORD)) {
			log.error("Service : Please provide valid username and password");
			throw new BusinessLogicException("Please provide valid username and password");
		}		
		else if(!ProductTypesEnums.getIdValueMap().containsValue(productName)) {
			log.error("Product Name does not exist");
			throw new BusinessLogicException("Please provide a valid Product Name");
		}
		log.debug("Service : Method: validateInventory end");
		return getInventoryFallback(productDTO);
	}
	
	
	public Inventory getInventoryFallback(ProductDTO productDTO) {
		Inventory inv=new Inventory();
		inv.setAvailableUnits(-1);
		Product product=new Product();
		product.setProductName(productDTO.getProductName());
		inv.setProduct(product);
		return inv;
	}
	
	
	public List<Inventory> bulkPostInventory(List<ProductDTO> productDTOList) {
		List<Inventory> list=new ArrayList<>();
		productDTOList.forEach(dto->{
			list.add(populatePostInventory(dto));
		});
		return inventoryRepository.saveAll(list);	
	}
	
	public Inventory populatePostInventory(ProductDTO productDTO) {
		Inventory inv=new Inventory();
		inv.setAvailableUnits(productDTO.getAvailableUnits());
		Product product=new Product();
		product.setProductName(productDTO.getProductName());
		product.setPrice(productDTO.getPrice());
		inv.setProduct(product);
		return inv;
	}
	
	public Inventory getInventory(String productName) {
		return inventoryRepository.findByProductProductName(productName);		
	}
	
	public List<Inventory> getInventory() {
		return inventoryRepository.findAll();
	}
	
	public String deleteInventory(String productName) {
		Inventory inv=inventoryRepository.findByProductProductName(productName);
		inventoryRepository.delete(inv);
		return "Deleted";
	}
	
	public String deleteInventory(List<Integer> ids) {
		List<Inventory> invList=inventoryRepository.findByIds(ids);
		inventoryRepository.deleteAll(invList);
		return "Deleted";
	}
	
	public Inventory UpdateInventory(ProductUpdateDTO productUpdateDTO) {
		Inventory inv=populateInventory(productUpdateDTO);
		return inventoryRepository.save(inv);		
	}
	
	public List<Inventory> bulkUpdateInventory(List<ProductUpdateDTO> productUpdateDTO) {
		List<Inventory> list=new ArrayList<>();
		productUpdateDTO.forEach(dto->list.add(populateInventory(dto)));
		return inventoryRepository.saveAll(list);		
	}
	
	public Inventory populateInventory(ProductUpdateDTO productUpdateDTO) {
		Inventory inv=inventoryRepository.findByProductProductName(productUpdateDTO.getProductName());
		if(productUpdateDTO.getOperation().equals("add")) {
			inv.setAvailableUnits(inv.getAvailableUnits()+productUpdateDTO.getUnits());
		}
		else {
			inv.setAvailableUnits(inv.getAvailableUnits()-productUpdateDTO.getUnits());
		}
		return inv;
	}
}
