package io.pivotal.edu.tests.integration;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.query.SelectResults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.bookshop.domain.BookMaster;
import io.pivotal.edu.gemfire.BookMasterRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicSpringClientTests {

	  @Resource(name="BookMaster")
	  Region<Integer, BookMaster> books;
	  
	  @Autowired
	  GemfireTemplate template;
	  
	  @Autowired
	  private BookMasterRepository repo;

	  
	  @Test
	  public void simpleClientTest() {
	    BookMaster book = books.get(456);
	    assertEquals("Clifford the Big Red Dog", book.getTitle());
	  }
	  
	  @Test
	  public void testGemFireTemplate() {
	    SelectResults<BookMaster> results = template.query("author = 'Daisy Mae West'");
	    assertEquals(1, results.size());
	    assertEquals("A Treatise of Treatises", results.asList().get(0).getTitle());
	  }
	  
	  @Test
	  public void testGemFireRepositories() {
	    List<BookMaster> results = repo.findByAuthor("Daisy Mae West");
	    assertEquals(1, results.size());
	    assertEquals("A Treatise of Treatises", results.get(0).getTitle());

	  }
}
