package io.pivotal.edu.gemfire;

import org.springframework.data.gemfire.repository.GemfireRepository;

import io.pivotal.bookshop.domain.Customer;

public interface CustomerRepository extends GemfireRepository<Customer, Integer> {

}
