package com.cos.book.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)   // 실제DB테스트 Replace.NONE, 내장DB테스트 Replace.ANY
@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;
	
	@Test
	public void save_테스트() {
		// given
		Book book = new Book(null, "책제목1", "책저자1");
		
		// when
		Book bookEntity = bookRepository.save(book);
	
		// then
		assertEquals("책제목1", bookEntity.getTitle());
	}
	
	@Test
	public void findAll_테스트() {
		// given
		bookRepository.saveAll(
				Arrays.asList(
						new Book(null, "스프링부트 따라하기", "코스"),
						new Book(null, "리엑트 따라하기", "코스")
				)
			);
		
		// when
		List<Book> bookEntitys = bookRepository.findAll();
		
		// then
		log.info("bookEntitys : "+bookEntitys.size() );
		assertNotEquals(0, bookEntitys.size());
		assertEquals(2, bookEntitys.size());
	}
	
}
