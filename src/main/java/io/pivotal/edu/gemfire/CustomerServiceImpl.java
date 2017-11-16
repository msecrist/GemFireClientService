package io.pivotal.edu.gemfire;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import io.pivotal.bookshop.domain.Customer;
import io.pivotal.edu.gemfire.support.AbstractCacheableService;

/**
 * Simulates a CacheLoader from the client side (assuming perhaps PCC)
 *
 * @author msecrist
 *
 */
@Service
public class CustomerServiceImpl extends AbstractCacheableService implements CustomerService {

	private final CustomerDbRepository customerDbRepo;

	@Resource(name = "Customer")
	private Region<Integer, Customer> customers;

	public CustomerServiceImpl(CustomerDbRepository customerDbRepo) {
		this.customerDbRepo = Optional.ofNullable(customerDbRepo)
			.orElseThrow(() -> new IllegalArgumentException("CustomerDbRepository is required"));
	}

	@Override
	public Map<Integer, Customer> getAllCustomers() {
		return this.customers.getAll(this.customers.keySetOnServer());
	}

	@Override
	public Customer getCustomerById(Integer customerNumber) {
		System.out.println("Attempting to fetch customer from GemFire");
		Customer customer = customers.get(customerNumber);
		if (customer == null) {
			System.out.println("Falling over to DB");
			customer = getCustomerByIdFromDb(customerNumber);
		}
		System.out.println("Customer = " + customer);
		return customer;
	}

	@Override
	@Cacheable("Customer")
	public Customer getCustomerByIdFromDb(Integer customerNumber) {

		setCacheMiss();
		System.err.printf("CACHE-MISS for Customer [%d]%n", customerNumber);

		return this.customerDbRepo.getCustomerById(customerNumber);
	}

	@Override
	public Optional<Customer> findCustomerByName(String firstName, String lastName) {
		return this.customerDbRepo.findCustomerByName(firstName, lastName);
	}
}
