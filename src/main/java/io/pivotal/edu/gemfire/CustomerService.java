package io.pivotal.edu.gemfire;

import java.util.Map;
import java.util.Optional;

import io.pivotal.bookshop.domain.Customer;

public interface CustomerService {

	boolean isCacheMiss();

	Map<Integer, Customer> getAllCustomers();

	Customer getCustomerById(Integer customerNumber);

	// While this shouldn't need to be here, it's the simplest way to ensure proper proxying
	Customer getCustomerByIdFromDb(Integer customerNumber);

	Optional<Customer> findCustomerByName(String firstName, String lastName);

}
