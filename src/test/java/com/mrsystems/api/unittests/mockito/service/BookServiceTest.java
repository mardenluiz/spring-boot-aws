package com.mrsystems.api.unittests.mockito.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.mrsystems.api.data.v1.BookVO;
import com.mrsystems.api.model.Book;
import com.mrsystems.api.repository.BookRepository;
import com.mrsystems.api.service.BookService;
import com.mrsystems.api.unittests.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook input;
    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository repository;
    @Spy
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllBooks() {
    }

    @Test
    void findById() {
        Book entity = input.mockBookEntity();
        entity.setId(1);

        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(entity));
        var result = bookService.findById(1);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Name Author 1", result.getAuthor());
        assertEquals(100.00, result.getPrice());
        assertEquals("Book Title 1", result.getTitle());

    }


    @Test
    void createNewBook() {
        Book entity = input.mockBookEntity();
        
        Book model = entity;
        model.setId(1);

        BookVO vo = input.mockBookVO(1);
        vo.setKey(1);
        
        Mockito.when(repository.save(entity)).thenReturn(model);
        var result = bookService.createNewBook(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Name Author 1", result.getAuthor());
        assertEquals(100.00, result.getPrice());
        assertEquals("Book Title 1", result.getTitle());
    }

    @Test
    void bookUpdate() {
    	
    	Book entity = input.mockBookEntity();
    	entity.setId(1);
        
        Book model = entity;
        model.setId(1);

        BookVO vo = input.mockBookVO(1);
        vo.setKey(1);
        
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(entity));
        Mockito.when(repository.save(entity)).thenReturn(model);
        var result = bookService.bookUpdate(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Name Author 1", result.getAuthor());
        assertEquals(100.00, result.getPrice());
        assertEquals("Book Title 1", result.getTitle());
        
    }

    @Test
    void bookDelete() {
    	
    	Book entity = input.mockBookEntity();
        entity.setId(1);

        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(entity));
        bookService.bookDelete(1);
    }
}