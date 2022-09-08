package com.ilyafrolov.LibraryDB;

import java.sql.SQLException;

public class Runner {
    public static void main(String[] args) {

        Library lib = new Library();
        lib.getLibrary();
        lib.printListOfBooks();
        lib.addBook("Riviera", "S. Hokking", "fantasy");
        lib.getLibrary();
        lib.printListOfBooks();
        lib.editBook(3, "Harry Potter 2", "Ivanov", "detective");
        lib.getLibrary();
        lib.printListOfBooks();
        lib.deleteBook(24);
        lib.deleteBook(25);
        lib.getLibrary();
        lib.printListOfBooks();
    }
}
