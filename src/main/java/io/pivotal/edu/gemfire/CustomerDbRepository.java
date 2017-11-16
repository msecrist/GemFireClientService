package io.pivotal.edu.gemfire;

import java.util.Optional;

import io.pivotal.bookshop.domain.Customer;

public interface CustomerDbRepository {

	Customer getCustomerById(Integer id) ;

	Optional<Customer> findCustomerByName(String firstName, String lastName);

}
