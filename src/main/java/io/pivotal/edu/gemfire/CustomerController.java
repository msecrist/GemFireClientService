package io.pivotal.edu.gemfire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public String  addCustomers(Integer customerId) {
		return service.getCustomerById(customerId).toString();
	}

}
