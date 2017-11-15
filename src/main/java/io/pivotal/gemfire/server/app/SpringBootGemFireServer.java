package io.pivotal.gemfire.server.app;

import static org.springframework.data.gemfire.util.ArrayUtils.asArray;

import java.io.File;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.server.CacheServer;
import org.apache.geode.cache.snapshot.SnapshotOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.CacheServerConfigurer;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;
import org.springframework.data.gemfire.snapshot.SnapshotServiceFactoryBean;

import io.pivotal.bookshop.domain.Customer;

/**
 * The {@link SpringBootGemFireServer} class is a {@link SpringBootApplication} class that bootstraps
 * a Pivotal GemFire peer {@link Cache} instance running a {@link CacheServer} to listen for [Spring Boot]
 * GemFire {@link ClientCache cache client} applications to connect.
 *
 * Additionally, this Spring Boot application class enables GemFire's embedded Locator and Management (Manager)
 * services, which allows JMX-enabled clients (e.g. Gfsh) to connect to this node.
 *
 * After replacing the $GEODE_HOME/tools/Extensions/geode-web-9.1.1.war file with geode-web-9.1.1-spring5.war
 * and renaming this file as geode-web-9.1.1.war, a connection attempt should be made to...
 *
 * connect --use-http --url=http://localhost:7070/gemfire/v1
 *
 * Finally, remember to load the snapshots when running the project (Integration & Unit) test suite.
 *
 * @author John Blum
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see org.springframework.boot.builder.SpringApplicationBuilder
 * @see org.springframework.data.gemfire.config.annotation.CacheServerApplication
 * @see org.springframework.data.gemfire.config.annotation.EnableLocator
 * @see org.springframework.data.gemfire.config.annotation.EnableManager
 * @see org.apache.geode.cache.Cache
 * @see org.apache.geode.cache.server.CacheServer
 * @since 1.0.0
 */
@SpringBootApplication
@CacheServerApplication
@EnableLocator
@EnableManager(start = true)
@SuppressWarnings("unused")
public class SpringBootGemFireServer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringBootGemFireServer.class).web(WebApplicationType.NONE).build(args).run();
	}

	@Bean
	CacheServerConfigurer cacheServerPortConfigurer(@Value("${gemfire.cache.server.port:40404}") int port) {
		return (beanName, cacheServerFactoryBean) -> cacheServerFactoryBean.setPort(port);
	}

	@Configuration
	@Profile("load-snapshots")
	@EnableEntityDefinedRegions(basePackageClasses = Customer.class)
	static class RegionAndSnapshotConfiguration {

		@Bean
		@DependsOn({ "BookMaster", "BookOrder", "Customer", "InventoryItem"})
		SnapshotServiceFactoryBean cacheSnapshotService(Cache gemfireCache) {

			SnapshotServiceFactoryBean<Object, Object> cacheSnapshotService = new SnapshotServiceFactoryBean<>();

			cacheSnapshotService.setCache(gemfireCache);
			cacheSnapshotService.setImports(asArray(newSnapshotMetadata(locateSnapshotsDirectory())));

			return cacheSnapshotService;
		}

		private File locateSnapshotsDirectory() {

			File baseDirectory = new File(System.getProperty("user.dir"));
			File serverBootstrapDirectory = new File(baseDirectory, "server-bootstrap");

			while (baseDirectory.getParentFile() != null && !serverBootstrapDirectory.isDirectory()) {
				baseDirectory = baseDirectory.getParentFile();
				serverBootstrapDirectory = new File(baseDirectory, "server-bootstrap");
			}

			if (!serverBootstrapDirectory.isDirectory()) {
				throw new RuntimeException("Unable to locate the server-bootstrap directory");
			}

			return new File(serverBootstrapDirectory, "data");
		}

		private SnapshotServiceFactoryBean.SnapshotMetadata<Object, Object> newSnapshotMetadata(File directory) {
			return new SnapshotServiceFactoryBean.SnapshotMetadata<>(directory, SnapshotOptions.SnapshotFormat.GEMFIRE);
		}
	}
}
