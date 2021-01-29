package com.cos.book.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.book.domain.Book;
import com.cos.book.service.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BookController {

	private final BookService bookService;
	
	@PostMapping("/book")
	public ResponseEntity<Book> save(@RequestBody Book book){
		return new ResponseEntity<Book>(bookService.저장하기(book), HttpStatus.CREATED); // 201
	}
	
	@GetMapping("/book")
	public ResponseEntity<List<Book>> findAll(){
		return new ResponseEntity<>(bookService.모두가져오기(), HttpStatus.OK); // 200
	}
	
	
}
