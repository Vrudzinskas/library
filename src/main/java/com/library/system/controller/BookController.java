package com.library.system.controller;

import com.library.system.entity.Book;
import com.library.system.entity.Message;
import com.library.system.exception.*;
import com.library.system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
public class BookController
{
    @Autowired
    BookService bookService;

    // add new book
    @PostMapping(path="/book")
    public ResponseEntity addNewBook(@RequestBody Book book )
    {
        try
        {
            bookService.addNewBook(book);
        }
        catch (NullJsonFieldException npe)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Message("Can't add new book without a title, and one or more authors and one or more categories"));
        }
        catch (BookAlreadyExistsException baee)
        {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new Message("Book already exists"));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Message("Book created"));
    }

    // remove book
    @DeleteMapping(path="/book")
    public ResponseEntity removeBook(@RequestBody Book book )
    {
        try
        {
            bookService.removeBook(book);
        }
        catch (NullJsonFieldException npe)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Message("Can't remove book without a title, and one or more authors and one or more categories"));
        }
        catch (BookNotFoundException bnfe)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new Message("Book not found"));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Message("Book deleted"));
    }

    // loan book
    @PostMapping(path="/loanbook/{userId}")
    public ResponseEntity loanBook(@PathVariable("userId") Long userId,@RequestBody Book book)
    {
        try
        {
            bookService.loanBook(userId, book);
        }
        catch (NullJsonFieldException npe)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Message("Can't loan book without a title, and one or more authors and one or more categories"));
        }
        catch (UserNotFoundException unfe)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new Message("User not found"));
        }
        catch (BookNotFoundException bnfe)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new Message("Book not found"));
        }
        catch (BookAlreadyLoanedException bale)
        {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new Message("Book already loaned"));
        }
        catch (UserNotAllowedToLoanMoreBooksException e)
        {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new Message("User not allowed to loan more books"));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Message("Book loaned"));
    }

    // return loaned book
    @PostMapping(path="/returnbook/{userId}")
    public ResponseEntity returnBook(@PathVariable("userId") Long userId,@RequestBody Book book)
    {
        try
        {
            bookService.returnBook(userId, book);
        }
        catch (NullJsonFieldException npe)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Message("Can't return book without a title, and one or more authors and one or more categories"));
        }
        catch (UserNotFoundException unfe)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new Message("User not found"));
        }
        catch (BookNotFoundException bnfe)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new Message("Book not found"));
        }
        catch (BookAlreadyReturnedException bare)
        {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new Message("Book already returned"));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Message("Book returned"));
    }
}
