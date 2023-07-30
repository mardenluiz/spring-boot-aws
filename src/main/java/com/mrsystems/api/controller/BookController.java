package com.mrsystems.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsystems.api.data.vo.v1.BookVO;
import com.mrsystems.api.data.vo.v1.PersonVO;
import com.mrsystems.api.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/book/v1")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/list")
    @Operation(summary = "Finds all Book", description = "Finds all Book",
	tags = {"Book"}, responses = {
		@ApiResponse(description = "Success", responseCode = "200",
				content = {
					@Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
					)
				}),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<List<BookVO>> findAllBooks() {
        return ResponseEntity.ok(service.findAllBooks());
    }
    

    @GetMapping("/{id}")
    @Operation(summary = "Find a Book", description = "Find a Book",
	tags = {"Book"}, responses = {
	@ApiResponse(description = "Success", responseCode = "200",
			content = { @Content(schema = @Schema(implementation = BookVO.class))}
		),
		@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
    public ResponseEntity<BookVO> findById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok().body(service.findById(id));
    }
    

    @PostMapping("/create")
	@Operation(summary = "Add a new Book",
		description = "Add a new Book by passing in a JSON or XML representation of the book",
		tags = {"Book"}, responses = {
		@ApiResponse(description = "Created", responseCode = "201",
				content = { @Content(schema = @Schema(implementation = BookVO.class))}
		),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
    public ResponseEntity<BookVO> createdNewBook(@RequestBody BookVO book ) {
        return new ResponseEntity<>(service.createNewBook(book), HttpStatus.CREATED);
    }

    
    @PutMapping("/update")
    @Operation(summary = "Update a new Book",
	description = "Updates a new Book by passing in a JSON or XML representation of the Book",
	tags = {"Book"}, responses = {
	@ApiResponse(description = "Updated", responseCode = "200",
			content = { @Content(schema = @Schema(implementation = PersonVO.class))}
	),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
    public ResponseEntity<BookVO> bookUpdate(@RequestBody BookVO  book) {
        return ResponseEntity.ok().body(service.bookUpdate(book));
    }
    

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Deletes a new Book",
	description = "Deletes a new Person by passing in a JSON or XML representation of the Book",
	tags = {"Book"}, responses = {
		@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Void> bookDelete(@PathVariable("id") Integer id) {
        service.bookDelete(id);
        return ResponseEntity.noContent().build();
    }
}
