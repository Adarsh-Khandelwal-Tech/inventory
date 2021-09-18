package hcl.practice.inventory.expectionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import hcl.practice.inventory.expection.BusinessLogicException;

@RestControllerAdvice
public class InventoryExceptionHandler {

	@ExceptionHandler(value = {BusinessLogicException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseErrorMessage handleBusinessLogicException(BusinessLogicException e, WebRequest request) {
		
		ResponseErrorMessage response=new ResponseErrorMessage();
		response.setErrorDescription(e.getErrorMessage());
		response.setStatus(HttpStatus.BAD_REQUEST);		
		return response;
	}
	
	@ExceptionHandler(value = {NumberFormatException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseErrorMessage handleNumberFormatException(NumberFormatException e, WebRequest request) {
		
		ResponseErrorMessage response=new ResponseErrorMessage();
		response.setErrorDescription(e.getMessage());
		response.setStatus(HttpStatus.BAD_REQUEST);		
		return response;
	}
}
