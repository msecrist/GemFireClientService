package io.pivotal.edu.gemfire;

import java.util.List;

import org.springframework.data.gemfire.repository.GemfireRepository;

import io.pivotal.bookshop.domain.BookMaster;

public interface BookMasterRepository  extends GemfireRepository<BookMaster, Integer> {
	List<BookMaster> findByAuthor(String authorName);
}
