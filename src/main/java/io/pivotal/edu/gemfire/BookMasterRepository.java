package io.pivotal.edu.gemfire;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.pivotal.bookshop.domain.BookMaster;

public interface BookMasterRepository  extends CrudRepository<BookMaster, Integer> {
	List<BookMaster> findByAuthor(String authorName);
}
