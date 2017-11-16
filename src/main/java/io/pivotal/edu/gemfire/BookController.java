package io.pivotal.edu.gemfire;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.bookshop.domain.BookMaster;

@RestController
public class BookController {

	@Resource(name = "BookMaster")
	Region<Integer, BookMaster> books;

	@RequestMapping("/books")
	public String books() {
		Map<Integer, BookMaster> allBooks = books.getAll(books.keySetOnServer());
		return allBooks.toString();

	}
}
