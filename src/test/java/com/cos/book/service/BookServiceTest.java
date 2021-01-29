package com.cos.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class) // 작은 단위의 단위 테스트시 사용
public class BookServiceTest {

	// 1. 서비스 Bean 필요  <- 2번으로 생성된 객체를 @InjectMocks를 이용해서 가짜 객체 주입
	@InjectMocks
	private BookService bookService;

	//2. 가짜 bookRepository 생성
	@Mock
	private BookRepository bookRepository;
	
	@Test
	public void 저장하기_테스트() {
		// given
		Book book = new Book();
		book.setTitle("책제목1");
		book.setAuthor("책저자1");
	
		// stub - 동작 지정
		when(bookRepository.save(book)).thenReturn(book);
		
		// test execute
		Book bookEntity = bookService.저장하기(book);
		
		// then
		assertEquals(bookEntity, book);
	}
}
