package io.pivotal.edu.tests.integration;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.bookshop.domain.Customer;
import io.pivotal.edu.gemfire.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment= WebEnvironment.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerServiceTest {

	@Autowired
	private CustomerService service;
	
	@Test
	public void testAGetCustomerFromGemFire() {
		Customer c = service.getCustomerById(5543);
		assertEquals("Failed", c.getFirstName(), "Lula");
		
	}
	
	@Test()
	public  void testBGetAllCustomersBeforeDBFetch() {
		Map<Integer, Customer> customers = service.getAllCustomers();
		assertEquals("Number of returned customers doesn't match", 3, customers.size());
	}
	
	@Test
	public void testCGetCustomerFromDb() {
		Customer c = service.getCustomerById(1);
		assertEquals("Failed", c.getFirstName(), "Mark");
	}
	
	@Test()
	public  void testDGetAllCustomersAfterDBFetch() {
		Map<Integer, Customer> customers = service.getAllCustomers();
		assertEquals("Number of returned customers doesn't match", 4,customers.size());
	}
	
}
