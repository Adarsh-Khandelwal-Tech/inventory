package hcl.practice.inventory.expection;

public class BusinessLogicException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	
	public BusinessLogicException(String errorMessage) {
		this.errorMessage=errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
		
}
