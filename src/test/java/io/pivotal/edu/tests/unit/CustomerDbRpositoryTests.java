package io.pivotal.edu.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import io.pivotal.bookshop.domain.Customer;
import io.pivotal.edu.gemfire.CustomerDbRepository;
import io.pivotal.edu.gemfire.CustomerDbRepositoryImpl;

public class CustomerDbRpositoryTests {

	@Test
	public void testValidFetch() {
		CustomerDbRepository customerRepository = new CustomerDbRepositoryImpl();
		Customer customer = customerRepository.getCustomerById(1);
		assertEquals("Failed", customer.getFirstName(), "Mark");
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidFetch() {
		CustomerDbRepository customerRepository = new CustomerDbRepositoryImpl();
		assertNotNull(customerRepository.getCustomerById(5));
	}
}
