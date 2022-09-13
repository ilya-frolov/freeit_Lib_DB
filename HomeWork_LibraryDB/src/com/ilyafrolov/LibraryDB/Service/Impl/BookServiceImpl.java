package com.ilyafrolov.LibraryDB.Service.Impl;

import com.ilyafrolov.LibraryDB.Pojo.Book;
import com.ilyafrolov.LibraryDB.DataSource.RepositorySupplier;
import com.ilyafrolov.LibraryDB.Service.BookService;

import java.util.List;

public final class BookServiceImpl implements BookService {

    public List<Book> getAllBooks() {
        return RepositorySupplier.getBookRepository().getAllBooks();
    }

    public void addBook(String title, String authorName, String genreName) {
        for (Book bookFromDB : getAllBooks()) {
            if (bookFromDB.getTitle().equals(title)) {
                System.out.println("A book with the title " + title + " already exists:");
                System.out.print(bookFromDB + "\n");
            }
        }

        RepositorySupplier.getBookRepository().insertInBooks(title, authorName, genreName);
        System.out.println("The book has been added successfully to the Library.");
    }

    public void editBook(int id, String newTitle, String newAuthor, String newGenre) {
        boolean idExist = false;
        for (Book bookFromDB : getAllBooks()) {
            if (bookFromDB.getId() == id) {
                idExist = true;
            }
        }

        if (idExist) {
            RepositorySupplier.getBookRepository().updateBook(id, newTitle, newAuthor, newGenre);
            System.out.println("The book has been edited successfully.");
        } else {
            System.out.println("The entered id '" + id + "' doesn't exist!");
        }
    }

    public void deleteBook(int id) {
        boolean idExist = false;
        for (Book bookFromDB : getAllBooks()) {
            if (bookFromDB.getId() == id) {
                idExist = true;
            }
        }

        if (idExist) {
            RepositorySupplier.getBookRepository().deleteBook(id);
            System.out.println("The book has been deleted successfully from the Library.");
        } else {
            System.out.println("The ID '" + id + "' doesn't exist.");
        }
    }

    public Book findBook(int id) {
        boolean idExist = false;
        for (Book bookFromDB : getAllBooks()) {
            if (bookFromDB.getId() == id) {
                idExist = true;
            }
        }

        Book book = new Book();
        if (idExist) {
            book = RepositorySupplier.getBookRepository().getBook(id);
        } else {
            System.out.println("The ID '" + id + "' doesn't exist.");
        }
        return book;
    }

}
