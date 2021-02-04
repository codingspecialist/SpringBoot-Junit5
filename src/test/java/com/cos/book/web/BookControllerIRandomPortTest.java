package com.cos.book.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.cos.book.domain.Book;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 통합 테스트 하는 법
 * 단점 : 모든 빈이 다 로드되기 때문에 통합 테스트가 가능하지만 느림!!
 * 장점 : 실제 서비스와 가장 유사하게 테스트 가능.
 * 팁 : SpringBootTest(class= {BookController.class, BookService.class, BookRepository.class}) 이렇게 필요한 빈만 올릴 수 도 있음.
 */

// TestRestTemplate 별로네... Mockito가 나음.
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // 실제 내장 톰켓이 랜덤 포트로 올라온다. 
public class BookControllerIRandomPortTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	private static HttpHeaders headers;
	
	@BeforeAll
	public static void init() {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	}
	
	@Test
	public void save_테스트() throws Exception {
		// given
		String content = new ObjectMapper().writeValueAsString(new Book(null, "스프링부트 따라하기", "코스"));
		HttpEntity<String> request = 
			      new HttpEntity<String>(content, headers);
		
		// when
		ResponseEntity<String> response = restTemplate.exchange("/book", HttpMethod.POST, request, String.class);
		
		// then
		assertEquals(201, response.getStatusCodeValue());
		System.out.println(response.getBody());
		//assertEquals("", response.getBody());
	}
	
	
	/**
	 *  참고 1
	 *  ResponseEntity<Book[]> response = restTemplate.getForEntity("/book",  Book[].class);
	 *  System.out.println(response.getBody()[0]);
	 *  참고 2
	 *  new ObjectMapper().writeValueAsString(자바오브젝트); // json으로 변경
	 *  new ObjectMapper().readValue(제이슨데이터, 자바오브젝트.class); // java object로 변경
	 */
	@Test
	public void findAll_테스트() {
		// given

		// when
		ResponseEntity<String> response = restTemplate.getForEntity("/book",  String.class);
		System.out.println(response.getBody());
		
		// then
		assertEquals(200, response.getStatusCodeValue());
	}
}
