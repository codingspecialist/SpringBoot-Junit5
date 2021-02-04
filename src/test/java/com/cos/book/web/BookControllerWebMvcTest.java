package com.cos.book.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.cos.book.domain.Book;
import com.cos.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 컨트롤러만 테스트하는 법
 * WebMvc 어노테이션
 * (1) AutoConfigureMockMvc 내장
 * (2) Controller, ControllerAdvice, JsonComponent, Filter, WebMvcConfigurer 등을 빈으로 올린다.
 */

/**
 * MockMvc 설명
 * perform() 수행
 * andDo() 이후 할 행동
 * andExpect() 기대값
 * 기대값에 content() 사용
 */

@WebMvcTest(BookController.class) 
public class BookControllerWebMvcTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@Test
	public void save_테스트() throws Exception {
		// given
		Book book = new Book(null, "스프링부트 따라하기", "코스");
		String content = new ObjectMapper().writeValueAsString(new Book(null, "스프링부트 따라하기", "코스"));
		when(bookService.저장하기(book)).thenReturn(new Book(1L, "스프링부트 따라하기", "코스")); // stub - 행동 정의
		
		// when
		ResultActions resultAction = mockMvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then
		resultAction
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.author").value("코스"))
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void findAll_테스트() throws Exception {
		// given
		List<Book> books = new ArrayList<>();
		books.add(new Book(1L, "스프링부트 따라하기", "코스"));
		books.add(new Book(2L, "리엑트 따라하기", "코스"));
		
		when(bookService.모두가져오기()).thenReturn(books); // stub - 행동 정의
		
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
