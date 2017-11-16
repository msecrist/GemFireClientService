package io.pivotal.edu.gemfire;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.bookshop.domain.BookMaster;

@RestController
public class BookController {
	@Resource(name="BookMaster")
	Region<Integer,BookMaster> books;
	
	@Autowired
	ClientCache cache;
	
	@RequestMapping("/books")
	public String books() {
		Map<Integer, BookMaster> allBooks= books.getAll(books.keySetOnServer());
		return allBooks.toString();
		
	}


}
