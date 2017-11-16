package io.pivotal.edu.gemfire;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.cache.config.EnableGemfireCaching;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableCachingDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import io.pivotal.bookshop.domain.BookMaster;
import io.pivotal.bookshop.domain.Customer;

@ClientCacheApplication(name="GemFireClient" )
@EnableGemfireRepositories
@EnableCachingDefinedRegions
public class GemFireConfiguration {

	@Bean("Customer")
	public ClientRegionFactoryBean<Integer, Customer> customerRegion (GemFireCache gemfireCache) {
		ClientRegionFactoryBean<Integer, Customer> customers = new ClientRegionFactoryBean<>();
		customers.setCache(gemfireCache);
		customers.setClose(true);
		customers.setName("Customer");
		customers.setShortcut(ClientRegionShortcut.PROXY);
		return customers;
	}
	
	@Bean("BookMaster")
	public ClientRegionFactoryBean<Integer, BookMaster> bookMasterRegion (GemFireCache gemfireCache) {
		ClientRegionFactoryBean<Integer, BookMaster> books = new ClientRegionFactoryBean<>();
		books.setCache(gemfireCache);
		books.setClose(true);
		books.setName("BookMaster");
		books.setShortcut(ClientRegionShortcut.PROXY);
		return books;
	}	
	
	@Bean
	@DependsOn("BookMaster")
	public GemfireTemplate template(GemFireCache gemfireCache) {
			
			return new GemfireTemplate(gemfireCache.getRegion("BookMaster"));
	}
	
}
