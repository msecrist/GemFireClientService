package io.pivotal.edu.tests.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;
import org.junit.Before;
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

	@Resource(name = "Customer")
	private Region<Integer, Customer> customers;

	@Autowired
	private CustomerService customerService;

	@Before
	public void setup() {

		this.customerService.findCustomerByName("Paul", "Chapman")
			.ifPresent(customer -> this.customers.remove(customer.getCustomerNumber()));

		this.customerService.findCustomerByName("Eitan", "Suez")
			.ifPresent(customer -> this.customers.remove(customer.getCustomerNumber()));
	}

	@Test
	public void testAGetCustomerFromGemFire() {

		Customer c = customerService.getCustomerById(5543);

		assertEquals("Failed", c.getFirstName(), "Lula");

	}

	@Test
	public void testBGetCustomerFromDbCachesAsExpected() {

		Customer paulChapman = customerService.getCustomerByIdFromDb(3);

		assertTrue(customerService.isCacheMiss());
		assertNotNull(paulChapman);
		assertEquals("Paul Chapman", paulChapman.getName());

		Customer paulChapmanAgain = customerService.getCustomerByIdFromDb(paulChapman.getCustomerNumber());

		assertFalse(customerService.isCacheMiss());
		assertEquals(paulChapman, paulChapmanAgain);

		Customer eitanSuez = customerService.getCustomerByIdFromDb(4);

		assertTrue(customerService.isCacheMiss());
		assertNotNull(eitanSuez);
		assertEquals("Eitan Suez", eitanSuez.getName());
	}

	@Test()
	public  void testCGetAllCustomersBeforeDBFetch() {

		Map<Integer, Customer> customers = customerService.getAllCustomers();

		assertEquals("Number of returned customers doesn't match", 3, customers.size());
	}

	@Test
	public void testDGetCustomerFromDb() {

		Customer customer = customerService.getCustomerById(1);

		assertTrue(customerService.isCacheMiss());
		assertEquals("Failed", customer.getFirstName(), "Mark");
	}

	@Test()
	public  void testEGetAllCustomersAfterDBFetch() {

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
