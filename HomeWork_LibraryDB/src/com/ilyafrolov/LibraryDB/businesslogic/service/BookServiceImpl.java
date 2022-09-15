package com.ilyafrolov.LibraryDB.businesslogic.service;

import com.ilyafrolov.LibraryDB.businesslogic.entity.Book;
import com.ilyafrolov.LibraryDB.database.RepositorySupplier;
import java.util.List;

public final class BookServiceImpl implements BookService {

    public List<Book> getAll() {
        return RepositorySupplier.getBookRepository().getAll();
    }

    public void add(Book book) {
        for (Book bookFromDB : getAll()) {
            if (bookFromDB.getTitle().equals(book.getTitle())) {
                System.out.println("A book with the title " + book.getTitle() + " already exists:");
                System.out.print(bookFromDB + "\n");
            }
        }

        RepositorySupplier.getBookRepository().add(book);
        System.out.println("The book has been added successfully to the Library.");
    }

    public void update(Book book) {
        boolean idExist = false;
        for (Book bookFromDB : getAll()) {
            if (bookFromDB.getId() == book.getId()) {
                idExist = true;
            }
        }

        if (idExist) {
            RepositorySupplier.getBookRepository().update(book);
            System.out.println("The book has been updated successfully.");
        } else {
            System.out.println("The entered id '" + book.getId() + "' doesn't exist!");
        }
    }

    public void delete(int id) {
        boolean idExist = false;
        for (Book bookFromDB : getAll()) {
            if (bookFromDB.getId() == id) {
                idExist = true;
            }
        }

        if (idExist) {
            RepositorySupplier.getBookRepository().delete(id);
            System.out.println("The book has been deleted successfully from the Library.");
        } else {
            System.out.println("The ID '" + id + "' doesn't exist.");
        }
    }

    public Book get(int id) {
        boolean idExist = false;
        for (Book bookFromDB : getAll()) {
            if (bookFromDB.getId() == id) {
                idExist = true;
            }
        }

        Book book = new Book();
        if (idExist) {
            book = RepositorySupplier.getBookRepository().get(id);
        } else {
            System.out.println("The ID '" + id + "' doesn't exist.");
        }
        return book;
    }

}
