package com.cos.book.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<?> save(@RequestBody Book book){
		return new ResponseEntity<Book>(bookService.저장하기(book), HttpStatus.CREATED); // 201
	}
	
	@GetMapping("/book")
	public ResponseEntity<?> findAll(){
		return new ResponseEntity<>(bookService.모두가져오기(), HttpStatus.OK); // 200
	}
	
	@GetMapping("/book/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		return new ResponseEntity<Book>(bookService.한건가져오기(id), HttpStatus.OK);
	}
	
	@PutMapping("/book/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book){
		return new ResponseEntity<>(bookService.수정하기(id, book), HttpStatus.OK);
	}
	
	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> update(@PathVariable Long id){
		return new ResponseEntity<>(bookService.삭제하기(id), HttpStatus.OK);
	}
}
