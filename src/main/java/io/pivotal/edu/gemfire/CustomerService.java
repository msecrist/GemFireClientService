package io.pivotal.edu.gemfire;

import java.util.Map;

import io.pivotal.bookshop.domain.Customer;

public interface CustomerService {

	Customer getCustomerById(Integer customerNumber);

	Map<Integer, Customer> getAllCustomers();

	// While this shouldn't need to be here, it's the simplest way to ensure proper
	// proxying
	Customer getCustomerFromDb(Integer customerNumber);

}
