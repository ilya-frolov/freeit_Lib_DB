package com.ilyafrolov.LibraryDB;

import java.sql.SQLException;

public class Runner {
    public static void main(String[] args) {

        Library lib = new Library();
        lib.getLibrary();
        lib.printListOfBooks();
        lib.addBook(new Book(2, "Java", new Author(2, "Blinov"), new Genre(5, "poem")));
        lib.getLibrary();
        lib.printListOfBooks();
    }
}
