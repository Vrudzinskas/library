package com.library.system.service;

import com.library.system.entity.Book;
import com.library.system.exception.BookAlreadyExistsException;
import com.library.system.exception.BookNotFoundException;

public interface BookService
{
    void addNewBook(Book requestBook);

    void removeBook(Book requestBook);

    void loanBook(Long userId, Book book);

    void returnBook(Long userId, Book book);
}
