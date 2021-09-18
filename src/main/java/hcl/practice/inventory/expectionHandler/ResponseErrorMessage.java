package hcl.practice.inventory.expectionHandler;

import org.springframework.http.HttpStatus;

public class ResponseErrorMessage {

	private HttpStatus status;
	private String errorDescription;
	
	
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	
}
