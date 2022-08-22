package com.library.system.repository;

import com.library.system.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>
{
    @Transactional
    @Modifying
    @Query("update book b set b.isAvalableToLoan=?1, b.libraryUser.libraryUserId=?2 where b.bookId=?3")
    void updateBookAvailableStatusAndLibraryUserId(boolean available, Long libraryUserId, Long bookId);

    @Query("select b from book b where b.libraryUser.libraryUserId =?1")
    List<Book> findAllBooksByLibraryUserId(Long libraryUserId);
}
