package com.cos.book.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cos.book.domain.Book;
import com.fasterxml.jackson.databind.ObjectMapper;

// https://itmore.tistory.com/entry/MockMvc-%EC%83%81%EC%84%B8%EC%84%A4%EB%AA%85
// https://wan-blog.tistory.com/71

// Mvc만 테스트
//@WebMvcTest(BookController.class)  // Controller, ControllerAdvice, JsonComponent, Filter, WebMvcConfigurer 등을 빈으로 올린다.

//통합테스트
@AutoConfigureMockMvc
@SpringBootTest
public class BookControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void save_테스트() throws Exception {
		// given
		Book book = new Book(1L, "책제목1", "책저자1");
		
		// // test execute and then
		mvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(book)))
		.andExpect(status().isCreated());
			
	}
}
