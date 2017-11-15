package io.pivotal.edu.gemfire;

import org.apache.geode.cache.GemFireCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.cache.config.EnableGemfireCaching;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import io.pivotal.bookshop.domain.Customer;

@ClientCacheApplication(name = "GemClientServiceApplication")
@EnableEntityDefinedRegions(basePackageClasses = Customer.class)
@EnableGemfireCaching
@EnableGemfireRepositories
@EnableClusterConfiguration(useHttp = true)
public class GemFireConfiguration {

	@Bean
	@DependsOn("BookMaster")
	public GemfireTemplate bookMasterTemplate(GemFireCache gemfireCache) {
		return new GemfireTemplate(gemfireCache.getRegion("/BookMaster"));
	}
}
