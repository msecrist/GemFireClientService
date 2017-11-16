package io.pivotal.edu.gemfire;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import io.pivotal.bookshop.domain.Address;
import io.pivotal.bookshop.domain.Customer;

@Repository
public class CustomerDbRepositoryImpl implements CustomerDbRepository {

	private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(0);

	private Map<Integer, Customer> customers = new HashMap<>();

	/**
	 * Populate a Map to simplify simulating a RDBMS
	 */
	public CustomerDbRepositoryImpl() {
		save("Mark", "Secrist");
		save("Bill", "Kable");
		save("Paul", "Chapman");
		save("Eitan", "Suez");
	}

	private Customer save(String firstName, String lastName) {
		return save(newCustomer(ID_SEQUENCE.incrementAndGet(), firstName, lastName));
	}

	private Customer save(Customer customer) {
		this.customers.put(customer.getCustomerNumber(), customer);
		return customer;
	}

	private Customer newCustomer(Integer customerNumber, String firstName, String lastName) {
		Customer customer = new Customer(customerNumber, firstName, lastName);
		customer.setPrimaryAddress(new Address("80526"));
		return customer;
	}

	/**
	 * Simulate a DB Fetch
	 */
	@Override
	public Customer getCustomerById(Integer id) {

		return Optional.ofNullable(id)
			.filter(it -> this.customers.containsKey(it))
			.map(this.customers::get)
			.orElseThrow(() -> new RuntimeException(String.format("Customer with key [%d] not found", id)));
	}

	public Optional<Customer> findCustomerByName(String firstName, String lastName) {

		return this.customers.values().stream()
			.filter(customer -> matchesByName(customer, firstName, lastName))
			.findFirst();
	}

	private boolean matchesByName(Customer customer, String firstName, String lastName) {
		return customer.getFirstName().equals(firstName) && customer.getLastName().equals(lastName);
	}
}
