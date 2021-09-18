package hcl.practice.inventory.entity;

import java.util.HashMap;
import java.util.Map;

public enum ProductTypesEnums {

	NEXDOM(1,"NEXDOM"),
	SARIDON(2,"SARIDON"),
	CORONIL(3,"CORONIL"),
	DOLO(4,"DOLO");
	
	private static Map<Integer,String> idValueMap=new HashMap<>();
	
	static {
		for(ProductTypesEnums product:ProductTypesEnums.values()) {
			idValueMap.put(product.getId(), product.getValue());
		}
	}
	
	private final Integer id;
	private final String value;
	
	
	ProductTypesEnums(final Integer id,final String value){
		this.id=id;
		this.value=value;
	}


	public static Map<Integer, String> getIdValueMap() {
		return idValueMap;
	}



	public Integer getId() {
		return id;
	}


	public String getValue() {
		return value;
	}
	
	
}
