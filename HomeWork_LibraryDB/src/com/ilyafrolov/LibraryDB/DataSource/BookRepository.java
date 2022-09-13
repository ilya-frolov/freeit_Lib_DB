package com.ilyafrolov.LibraryDB.DataSource;

import com.ilyafrolov.LibraryDB.Pojo.Book;

import java.util.List;

public interface BookRepository {

    List<Book> getAllBooks();

    void insertInBooks(String title, String authorName, String genreName);

    void updateBook(int id, String newTitle, String newAuthor, String newGenre);

    void deleteBook(int id);

    Book getBook(int id);
}
