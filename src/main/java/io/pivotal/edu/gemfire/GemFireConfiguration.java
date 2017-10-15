package io.pivotal.edu.gemfire;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import io.pivotal.bookshop.domain.BookMaster;
import io.pivotal.bookshop.domain.Customer;




@ClientCacheApplication(name="GemFireClient" )
@EnableGemfireRepositories
public class GemFireConfiguration {

	@Bean
	public ClientRegionFactoryBean<Integer, Customer> customerRegion (GemFireCache gemfireCache) {
		ClientRegionFactoryBean<Integer, Customer> customers = new ClientRegionFactoryBean<>();
		customers.setCache(gemfireCache);
		customers.setClose(true);
		customers.setName("Customer");
		customers.setShortcut(ClientRegionShortcut.PROXY);
		return customers;
	}
	
	@Bean
	public ClientRegionFactoryBean<Integer, BookMaster> bookMasterRegion (GemFireCache gemfireCache) {
		ClientRegionFactoryBean<Integer, BookMaster> books = new ClientRegionFactoryBean<>();
		books.setCache(gemfireCache);
		books.setClose(true);
		books.setName("BookMaster");
		books.setShortcut(ClientRegionShortcut.PROXY);
		return books;
	}	
	
	@Bean
	@Autowired
	public GemfireTemplate template(@Qualifier("bookMasterRegion") ClientRegionFactoryBean<Integer, BookMaster> bookMaster) {
			
			return new GemfireTemplate(bookMaster.getRegion());
	}
	
}
