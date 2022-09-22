package com.ilyafrolov.LibraryDB.database;

import com.ilyafrolov.LibraryDB.businesslogic.entity.Book;

import java.util.List;

public interface BookRepository {

    List<Book> getAll();

    void add(Book book);

    void update(Book book);

    void delete(int id);

    Book getById(int id);

    Book getByName(String title);
}
