package com.mrsystems.api.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrsystems.api.controller.BookController;
import com.mrsystems.api.data.vo.v1.BookVO;
import com.mrsystems.api.exceptions.RequiredObjectNotFoundExcption;
import com.mrsystems.api.exceptions.ResourceNotFoundException;
import com.mrsystems.api.model.Book;
import com.mrsystems.api.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private ModelMapper mapper;

    public List<BookVO> findAllBooks() {

        var type  = mapper.typeMap(Book.class, BookVO.class);
        type.addMappings(map -> map.map(Book::getId, BookVO::setKey));

        List<BookVO> entity = repository.findAll().stream().map(e -> mapper.map(e, BookVO.class)).toList();
        entity
            .forEach(e -> e.add(linkTo(methodOn(BookController.class)
                    .findById(e.getKey())).withSelfRel()));

        return entity;
    }

    public BookVO findById(Integer id) {

        var type  = mapper.typeMap(Book.class, BookVO.class);
        type.addMappings(map -> map.map(Book::getId, BookVO::setKey));

        var model = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var entity = mapper.map(model, BookVO.class);

        entity.add(linkTo(methodOn(BookController.class).findById(entity.getKey())).withSelfRel());
        return mapper.map(entity, BookVO.class);
    }

    public BookVO createNewBook(BookVO book) {

        if (book == null) throw new RequiredObjectNotFoundExcption();

        var type  = mapper.typeMap(BookVO.class, Book.class);
        type.addMappings(map -> map.map(BookVO::getKey, Book::setId));
        
        var model = mapper.map(book, Book.class);
        var vo = mapper.map(repository.save(model), BookVO.class);
        
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO bookUpdate(BookVO book) {

        var type  = mapper.typeMap(Book.class, BookVO.class);
        type.addMappings(map -> map.map(Book::getId, BookVO::setKey));

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setPrice(book.getPrice());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setTitle(book.getTitle());

        var vo = mapper.map(repository.save(entity), BookVO.class);

        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void bookDelete(Integer id) {

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }
}
