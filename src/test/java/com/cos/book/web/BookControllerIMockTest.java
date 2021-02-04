package com.cos.book.web;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 통합 테스트 하는 법
 * 단점 : 모든 빈이 다 로드되기 때문에 통합 테스트가 가능하지만 느림!!
 * 장점 : 실제 서비스와 가장 유사하게 테스트 가능.
 * 팁 : SpringBootTest(class= {BookController.class, BookService.class, BookRepository.class}) 이렇게 필요한 빈만 올릴 수 도 있음.
 */

@Transactional // 모든 트랜잭션 작업이 각각의 테스트 종료후 rollback 된다.
@AutoConfigureMockMvc // MockMvc를 빈으로 등록해준다. 
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) // 서블릿을 mocking 한것이 동작한다. (내장 톰켓 사용 안함)
public class BookControllerIMockTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Test
	public void save_테스트() throws Exception {
		// given
		String content = new ObjectMapper().writeValueAsString(new Book(null, "스프링부트 따라하기", "코스"));

		// when
		ResultActions resultAction = mockMvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then
		resultAction
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(1L))
			.andExpect(jsonPath("$.author").value("코스"))
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void findAll_테스트() throws Exception {
		// given
		bookRepository.saveAll(
				Arrays.asList(
						new Book(null, "스프링부트 따라하기", "코스"),
						new Book(null, "리엑트 따라하기", "코스")
				)
			);
		
		// when
		ResultActions resultAction = mockMvc.perform(get("/book")
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then
		resultAction
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.*", Matchers.hasSize(2))) // import static org.hamcrest.Matchers.*; 임포트하면 편한게 사용가능
			.andExpect(jsonPath("$.[0].title").value("스프링부트 따라하기"))
			.andExpect(jsonPath("$.[0].author").value("코스"))
			.andDo(MockMvcResultHandlers.print());

	}
}
