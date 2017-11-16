package io.pivotal.edu.gemfire;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import io.pivotal.bookshop.domain.Address;
import io.pivotal.bookshop.domain.Customer;

@Repository
public class CustomerDbRepositoryImpl implements CustomerDbRepository {
	private Map<Integer, Customer> customers = new HashMap<>();

	/*
	 * Populate a HashMap to simplify simulating a RDBMS
	 */
	public CustomerDbRepositoryImpl() {
		customers.put(1, createCustomer("Mark", "Secrist", 1));
		customers.put(2, createCustomer("Bill", "Kable", 2));
		customers.put(3, createCustomer("Paul", "Chapman", 3));
		customers.put(4, createCustomer("Eitan", "Suez", 4));
	}
	
	/**
	 * Simulate a DB Fetch
	 */
	@Override
	@Cacheable("Customer")
	public Customer getCustomerById(Integer id) {
		if (! customers.containsKey(id))
			throw new RuntimeException("Customer with key: " + id + " not found");
		return customers.get(id);
	}
	
	private Customer createCustomer(String firstName, String lastName, Integer customerNumber) {
		Customer newCust = new Customer(customerNumber, firstName, lastName);
		newCust.setPrimaryAddress(new Address("80526"));
		return newCust;
	}

}
