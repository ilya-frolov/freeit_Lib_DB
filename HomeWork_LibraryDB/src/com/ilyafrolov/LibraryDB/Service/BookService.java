package com.ilyafrolov.LibraryDB.Service;

import com.ilyafrolov.LibraryDB.Pojo.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    void addBook(String title, String authorName, String genreName);

    void editBook(int id, String newTitle, String newAuthor, String newGenre);

    void deleteBook(int id);

    Book findBook(int id);

}
