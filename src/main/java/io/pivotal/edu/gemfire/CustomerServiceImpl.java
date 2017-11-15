package io.pivotal.edu.gemfire;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import io.pivotal.bookshop.domain.Customer;

/**
 * Simulates a CacheLoader from the client side (assuming perhaps PCC)
 *
 * @author msecrist
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerDbRepository customerDbRepo;

	@Resource(name = "Customer")
	private Region<Integer, Customer> customers;

	public CustomerServiceImpl(CustomerDbRepository customerDbRepo) {
		this.customerDbRepo = customerDbRepo;
	}

	@Override
	public Customer getCustomerById(Integer customerNumber) {
		System.out.println("Attempting to fetch customer from GemFire");
		Customer customer = customers.get(customerNumber);
		if (customer == null) {
			System.out.println("Falling over to DB");
			customer = getCustomerFromDb(customerNumber);
		}
		System.out.println("Customer = " + customer);
		return customer;
	}

	@Override
	public Map<Integer, Customer> getAllCustomers() {
		return customers.getAll(customers.keySetOnServer());
	}

	@Override
	@Cacheable("Customer")
	public Customer getCustomerFromDb(Integer customerNumber) {
		System.err.printf("CACHE-MISS for Customer [%d]%n", customerNumber);
		return customerDbRepo.getCustomerById(customerNumber);
	}
}
