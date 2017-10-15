package io.pivotal.edu.gemfire;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.bookshop.domain.Customer;

@RestController
public class CustomerController {
	@Resource(name="customerRegion")
	Region<Integer,Customer> customers;
	
	@RequestMapping("/customers")
	public String customers() {
		Map<Integer, Customer> cust = customers.getAll(customers.keySetOnServer());
		return cust.toString();
		
	}

}
