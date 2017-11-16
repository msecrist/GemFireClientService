package io.pivotal.edu.gemfire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService service;
		
	@RequestMapping("/customers")
	public String customers() {		
		return service.getAllCustomers().toString();
	}
	
	@RequestMapping("/customer/{customerId}")
	public String  getCustomer(@PathVariable Integer customerId) {
		System.out.println("customerId: " + customerId);
		return service.getCustomerById(customerId).toString();
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Requested resource not found")
	@ExceptionHandler(RuntimeException.class)
	public void handleException(RuntimeException exception) {
		System.out.println("Caught exception: "  + exception);
	}

}
