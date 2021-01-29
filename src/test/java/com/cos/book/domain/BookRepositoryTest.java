package com.cos.book.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)   // 실제DB테스트 Replace.NONE, 내장DB테스트 Replace.ANY
@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;
	
	@Test
	public void save_테스트() { // 실제 DB로 테스트 하고 Rollback 된다.
		// given
		Book book = new Book(null, "책제목1", "책저자1");
		
		// stub 동작 지정이 필요 없음. 왜냐하면 테스트 DB로 확인할 것이니까!!
		// when(bookRepository.save(book)).thenReturn(new Book(null, "책제목2", "책저자2"));
		
		// test execute
		Book bookEntity = bookRepository.save(book);
		
		// then
		assertEquals("책제목1", bookEntity.getTitle());
	}
	
}
