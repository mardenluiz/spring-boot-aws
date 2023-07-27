package com.mrsystems.api.unittests.mocks;

import com.mrsystems.api.data.v1.BookVO;
import com.mrsystems.api.model.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockBook {

    public Book mockBookEntity() {
        return mockBookEntity(1);
    }

    public BookVO mockBookVO() {
        return mockBookVO(1);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockBookEntity(i));
        }
        return books;
    }

    public List<BookVO> mockBookVOList() {
        List<BookVO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockBookVO(i));
        }
        return books;
    }

    public Book mockBookEntity(Integer i) {
    	
        Book book = new Book();
        book.setId(i.intValue());
        book.setAuthor("Name Author " + i);
        book.setLaunchDate(LocalDate.now());
        book.setPrice(100.00);
        book.setTitle("Book Title " + i);

        return book;
    }

    public BookVO mockBookVO(Integer i) {
    	
        BookVO bookVO = new BookVO();
        bookVO.setKey(i.intValue());
        bookVO.setAuthor("Name Author " + i);
        bookVO.setLaunchDate(LocalDate.now());
        bookVO.setPrice(100.00);
        bookVO.setTitle("Book Title " + i);
        
        return bookVO;
    }
}
