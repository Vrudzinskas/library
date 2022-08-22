package com.library.system.entity;

import com.sun.istack.NotNull;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book
{
    @Id
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    private Long bookId;

    private String title;
    private boolean isAvalableToLoan = true;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "libraryUserId",
            referencedColumnName = "libraryUserId"
    )
    private LibraryUser libraryUser;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_author_mapping",
            joinColumns = @JoinColumn(name="book_id", referencedColumnName = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "author_id",referencedColumnName = "authorId"))
    private List<Author> authorList;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_category_mapping",
    joinColumns = @JoinColumn(name="book_id", referencedColumnName = "bookId"),
    inverseJoinColumns = @JoinColumn(name = "category_id",referencedColumnName = "categoryId"))
    private List<Category> categoryList;

    public void addCategory(Category category)
    {
        if(categoryList == null) categoryList = new ArrayList<>();
        categoryList.add(category);
    }

    public void addAuthor(Author author)
    {
        if(authorList == null) authorList = new ArrayList<>();
        authorList.add(author);
    }
}
