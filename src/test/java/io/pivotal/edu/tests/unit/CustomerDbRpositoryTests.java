package io.pivotal.edu.tests.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.pivotal.bookshop.domain.Customer;
import io.pivotal.edu.gemfire.CustomerDbRepository;
import io.pivotal.edu.gemfire.CustomerDbRepositoryImpl;

public class CustomerDbRpositoryTests {

	@Test
	public void testValidFetch() {
		CustomerDbRepository cr = new CustomerDbRepositoryImpl();
		Customer c = cr.getCustomerById(1);
		assertEquals("Failed", c.getFirstName(), "Mark");
	}
	
	@Test(expected=RuntimeException.class)
	public void testInvalidFetch() {
		CustomerDbRepository cr = new CustomerDbRepositoryImpl();
		Customer c = cr.getCustomerById(5);
	}
}
