package com.library.system.service;

import com.library.system.entity.Author;
import com.library.system.entity.Book;
import com.library.system.entity.LibraryUser;
import com.library.system.exception.*;
import com.library.system.repository.BookRepository;
import com.library.system.repository.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService
{
    @Autowired
    BookRepository bookRepository;

    @Autowired
    LibraryUserRepository libraryUserRepository;


    @Value("${books.allowed}")
    int numberOfBooksAllowedToLoan;

    @Override
    public void addNewBook(Book requestBook) throws BookAlreadyExistsException
    {
        if (!validateIncomingBook(requestBook))throw new NullJsonFieldException();

        List<Book> books = bookRepository.findAll();
        boolean sameTitle=false;
        boolean sameAuthor=false;

        for(Book databaseBook: books)
        {
            if (databaseBook.getTitle().trim().equalsIgnoreCase(requestBook.getTitle().trim()))
            {
                sameTitle = true;
                List<Author> requestBookAuthors = requestBook.getAuthorList();
                List<Author> databaseBookAuthors = databaseBook.getAuthorList();
                for (Author requestBookAuthor: requestBookAuthors)
                {
                    for (Author databaseBookAuthor: databaseBookAuthors)
                    {
                        if (requestBookAuthor.getFirstName().equals(databaseBookAuthor.getFirstName()) &&
                                requestBookAuthor.getLastName().equals(databaseBookAuthor.getLastName()))
                        {
                            sameAuthor=true;
                        }
                    }
                }
            }
        }

        if (sameTitle && sameAuthor)
        {
            throw new BookAlreadyExistsException();
        }

        requestBook.setAvalableToLoan(true);
        bookRepository.save(requestBook);
    }

    @Override
    public void removeBook(Book requestBook) throws BookNotFoundException
    {
        if (!validateIncomingBook(requestBook))throw new NullJsonFieldException();

        List<Book> books = bookRepository.findAll();
        boolean sameTitle=false;
        boolean sameAuthor=false;
        Long bookId = 0L;

        for(Book databaseBook: books)
        {
            if (databaseBook.getTitle().trim().equalsIgnoreCase(requestBook.getTitle().trim()))
            {
                sameTitle = true;
                List<Author> requestBookAuthors = requestBook.getAuthorList();
                List<Author> databaseBookAuthors = databaseBook.getAuthorList();
                for (Author requestBookAuthor: requestBookAuthors)
                {
                    for (Author databaseBookAuthor: databaseBookAuthors)
                    {
                        if (requestBookAuthor.getFirstName().equals(databaseBookAuthor.getFirstName())&&
                            requestBookAuthor.getLastName().equals(databaseBookAuthor.getLastName()))
                        {
                            sameAuthor=true;
                            bookId = databaseBook.getBookId();
                        }
                    }
                }
            }
        }

        if (!sameTitle || !sameAuthor) throw new BookNotFoundException();

        bookRepository.deleteById(bookId);
    }

    @Override
    public void loanBook(Long userId, Book requestBook)
    {
        if (!validateIncomingBook(requestBook))throw new NullJsonFieldException();

        // Get user
        Optional<LibraryUser> userOptional = libraryUserRepository.findById(userId);
        if(userOptional.isEmpty()) throw new UserNotFoundException();

        LibraryUser libraryUser = userOptional.get();

        // Get Book id
        List<Book> books = bookRepository.findAll();
        boolean sameTitle=false;
        boolean sameAuthor=false;
        Long bookId = 0L;

        for(Book databaseBookInList: books)
        {
            if (databaseBookInList.getTitle().trim().equalsIgnoreCase(requestBook.getTitle().trim()))
            {
                sameTitle = true;
                List<Author> requestBookAuthors = requestBook.getAuthorList();
                List<Author> databaseBookAuthors = databaseBookInList.getAuthorList();
                for (Author requestBookAuthor: requestBookAuthors)
                {
                    for (Author databaseBookAuthor: databaseBookAuthors)
                    {
                        if (requestBookAuthor.getFirstName().equals(databaseBookAuthor.getFirstName())&&
                                requestBookAuthor.getLastName().equals(databaseBookAuthor.getLastName()))
                        {
                            sameAuthor=true;
                            bookId = databaseBookInList.getBookId();
                        }
                    }
                }
            }
        }
        if (!sameTitle || !sameAuthor) throw new BookNotFoundException();

        // check if loaned already
        Book databaseBook = bookRepository.findById(bookId).get();
        if (!databaseBook.isAvalableToLoan()) throw new BookAlreadyLoanedException();

        // check if allowed to loan
        List<Book> loanedBooks = bookRepository.findAllBooksByLibraryUserId(userId);
        if (loanedBooks.size() >= numberOfBooksAllowedToLoan)
            throw new UserNotAllowedToLoanMoreBooksException();

        bookRepository.updateBookAvailableStatusAndLibraryUserId(false, userId, bookId);
    }

    @Override
    public void returnBook(Long userId, Book requestBook)
    {
        if (!validateIncomingBook(requestBook))throw new NullJsonFieldException();

        // Get User
        Optional<LibraryUser> userOptional = libraryUserRepository.findById(userId);
        if(userOptional.isEmpty()) throw new UserNotFoundException();

        // Get BookId
        List<Book> books = bookRepository.findAll();
        boolean sameTitle=false;
        boolean sameAuthor=false;
        Long bookId = 0L;

        for(Book databaseBook: books)
        {
            if (databaseBook.getTitle().trim().equalsIgnoreCase(requestBook.getTitle().trim()))
            {
                sameTitle = true;
                List<Author> requestBookAuthors = requestBook.getAuthorList();
                List<Author> databaseBookAuthors = databaseBook.getAuthorList();
                for (Author requestBookAuthor: requestBookAuthors)
                {
                    for (Author databaseBookAuthor: databaseBookAuthors)
                    {
                        if (requestBookAuthor.getFirstName().equals(databaseBookAuthor.getFirstName())&&
                                requestBookAuthor.getLastName().equals(databaseBookAuthor.getLastName()))
                        {
                            sameAuthor=true;
                            bookId = databaseBook.getBookId();
                        }
                    }
                }
            }
        }
        if (!sameTitle || !sameAuthor) throw new BookNotFoundException();

        // check if returned already
        Book databaseBook = bookRepository.findById(bookId).get();
        if (databaseBook.isAvalableToLoan()) throw new BookAlreadyReturnedException();

        bookRepository.updateBookAvailableStatusAndLibraryUserId(true, null, bookId);
    }

    public boolean validateIncomingBook(Book book)
    {
        if ((book.getTitle()==null || book.getCategoryList()==null || book.getAuthorList()==null))
            return false;
        return true;
    }
}
