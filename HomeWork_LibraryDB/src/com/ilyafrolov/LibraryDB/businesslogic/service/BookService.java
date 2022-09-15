package com.ilyafrolov.LibraryDB.businesslogic.service;

import com.ilyafrolov.LibraryDB.businesslogic.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    void add(Book book);

    void update(Book book);

    void delete(int id);

    Book get(int id);

}
