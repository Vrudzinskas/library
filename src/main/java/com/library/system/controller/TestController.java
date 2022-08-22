package com.library.system.controller;

import com.library.system.entity.Author;
import com.library.system.entity.Book;
import com.library.system.entity.Category;
import com.library.system.entity.LibraryUser;
import com.library.system.repository.BookRepository;
import com.library.system.repository.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController
{
    @Autowired
    BookRepository bookRepository;

    @Autowired
    LibraryUserRepository userRepository;

    @GetMapping("/populate")
    public void bigtest()
    {
        Category category1 = Category.builder()
                .category("Category1")
                .build();

        Category category2 = Category.builder()
                .category("Category2")
                .build();

        Author author1 = Author.builder()
                .firstName("FirstName1")
                .lastName("LastName1")
                .build();

        Author author2 = Author.builder()
                .firstName("FirstName2")
                .lastName("LastName2")
                .build();

        Book book = Book.builder()
                .title("Some book")
                .isAvalableToLoan(true)
                .authorList(List.of(author1, author2))
                .categoryList(List.of(category1,category2))
                .build();
        Book book2 = Book.builder()
                .title("Some other book")
                .isAvalableToLoan(true)
                .authorList(List.of(author1, author2))
                .categoryList(List.of(category1,category2))
                .build();

        Book book3 = Book.builder()
                .title("The third book")
                .isAvalableToLoan(true)
                .authorList(List.of(author1, author2))
                .categoryList(List.of(category1,category2))
                .build();

        Book book4 = Book.builder()
                .title("Book, The Fourth !")
                .isAvalableToLoan(true)
                .authorList(List.of(author1, author2))
                .categoryList(List.of(category1,category2))
                .build();

        bookRepository.save(book);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);

        LibraryUser user1 = LibraryUser.builder()
                .firstName("John")
                .lastName("Marston")
                .build();

        LibraryUser user2 = LibraryUser.builder()
                .firstName("Jack")
                .lastName("Black")
                .build();

        LibraryUser user3 = LibraryUser.builder()
                .firstName("Martin")
                .lastName("Madrazzo")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }
}
