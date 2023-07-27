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

import com.mrsystems.api.data.v1.PersonVO;
import com.mrsystems.api.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {

	private final PersonService service;

	public PersonController(PersonService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	@Operation(summary = "Find a People", description = "Find a People",
			tags = {"People"}, responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = { @Content(schema = @Schema(implementation = PersonVO.class))}
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public PersonVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}

	@GetMapping(value = "/list")
	@Operation(summary = "Finds all People", description = "Finds all People",
		tags = {"People"}, responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {
						@Content(
								mediaType = "application/json",
								array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
						)
					}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public List<PersonVO> findAll() {
		return service.findAll();
	}

	@PostMapping("/create")
	@Operation(summary = "Add a new Person",
			description = "Add a new Person by passing in a JSON or XML representation of the person",
			tags = {"People"}, responses = {
			@ApiResponse(description = "Created", responseCode = "201",
					content = { @Content(schema = @Schema(implementation = PersonVO.class))}
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<PersonVO> create(@RequestBody PersonVO person) {
		return new ResponseEntity<>(service.create(person), HttpStatus.CREATED);
	}

	@PutMapping("/update")
	@Operation(summary = "Update a new Person",
			description = "Updates a new Person by passing in a JSON or XML representation of the person",
			tags = {"People"}, responses = {
			@ApiResponse(description = "Updated", responseCode = "200",
					content = { @Content(schema = @Schema(implementation = PersonVO.class))}
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public PersonVO update(@RequestBody PersonVO person) {
		return service.update(person);
	}


	@DeleteMapping("/{id}")
	@Operation(summary = "Deletes a new Person",
			description = "Deletes a new Person by passing in a JSON or XML representation of the person",
			tags = {"People"}, responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
