package io.pivotal.edu.gemfire;

import io.pivotal.bookshop.domain.Customer;

public interface CustomerDbRepository {
	Customer getCustomerById(Integer id) ;
}
