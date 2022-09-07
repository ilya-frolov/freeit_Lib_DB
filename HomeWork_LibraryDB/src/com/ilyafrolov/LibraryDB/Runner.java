package com.ilyafrolov.LibraryDB;

import java.sql.SQLException;

public class Runner {
    public static void main(String[] args) {

        Library lib = new Library();
        lib.getLibrary();
        lib.printListOfBooks();
        lib.addBook("Java", "Blinov", "horror");
        lib.getLibrary();
        lib.printListOfBooks();
        lib.editBook(11, "Java programming", "Ivanov", "fairy tales");
        lib.getLibrary();
        lib.printListOfBooks();
    }
}
