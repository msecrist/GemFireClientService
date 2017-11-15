package io.pivotal.edu.tests.integration;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.bookshop.domain.Customer;
import io.pivotal.edu.gemfire.CustomerDbRepository;
import io.pivotal.edu.gemfire.CustomerDbRepositoryImpl;
import io.pivotal.edu.gemfire.CustomerService;
import io.pivotal.edu.gemfire.CustomerServiceImpl;
import io.pivotal.edu.gemfire.GemFireConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= WebEnvironment.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;

	@Test
	public void testAGetCustomerFromGemFire() {

		Customer c = customerService.getCustomerById(5543);

		assertEquals("Failed", c.getFirstName(), "Lula");

	}

	@Test()
	public  void testBGetAllCustomersBeforeDBFetch() {

		Map<Integer, Customer> customers = customerService.getAllCustomers();

		assertEquals("Number of returned customers doesn't match", 3, customers.size());
	}

	@Test
	public void testCGetCustomerFromDb() {

		Customer customer = customerService.getCustomerById(1);

		assertEquals("Failed", customer.getFirstName(), "Mark");
	}

	@Test()
	public  void testDGetAllCustomersAfterDBFetch() {

		Map<Integer, Customer> customers = customerService.getAllCustomers();

		assertEquals("Number of returned customers doesn't match", 3, customers.size());
	}

	@Configuration
	@Import(GemFireConfiguration.class)
	static class TestConfiguration {

		@Bean
		CustomerDbRepository customerDbRepository() {
			return new CustomerDbRepositoryImpl();
		}

		@Bean
		CustomerService customerService(CustomerDbRepository customerRepository) {
			return new CustomerServiceImpl(customerRepository);
		}
	}
}
