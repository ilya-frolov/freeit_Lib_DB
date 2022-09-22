package com.ilyafrolov.LibraryDB.businesslogic.service.impl;

import com.ilyafrolov.LibraryDB.businesslogic.entity.Author;
import com.ilyafrolov.LibraryDB.businesslogic.entity.Book;
import com.ilyafrolov.LibraryDB.businesslogic.entity.Genre;
import com.ilyafrolov.LibraryDB.businesslogic.service.BookService;
import com.ilyafrolov.LibraryDB.database.impl.AuthorRepositoryImpl;
import com.ilyafrolov.LibraryDB.database.impl.BookRepositoryImpl;
import com.ilyafrolov.LibraryDB.database.impl.GenreRepositoryImpl;

import java.util.List;

public final class BookServiceImpl implements BookService {

    public static final AuthorServiceImpl AUTHOR_SERVICE = new AuthorServiceImpl();
    public static final GenreServiceImpl GENRE_SERVICE = new GenreServiceImpl();
    private static final BookRepositoryImpl BOOK_REPOSITORY = new BookRepositoryImpl();
    private static final AuthorRepositoryImpl AUTHOR_REPOSITORY = new AuthorRepositoryImpl();
    private static final GenreRepositoryImpl GENRE_REPOSITORY = new GenreRepositoryImpl();

    public List<Book> getAll() {
        return BOOK_REPOSITORY.getAll();
    }

    public void add(Book book) {

        if (isPresentTitle(book.getTitle())) {
            System.out.println("Book(s) with the title " + book.getTitle() + " already exist(s):");
            System.out.println(BOOK_REPOSITORY.getByName(book.getTitle()));
        }

        if (AUTHOR_SERVICE.isPresent(book.getAuthor())) {
            book.setAuthor(AUTHOR_SERVICE.get(book.getAuthor()));
        } else {
            Author newAuthor = AUTHOR_REPOSITORY.add(book.getAuthor());
            book.setAuthor(newAuthor);
        }

        if (GENRE_SERVICE.isPresent(book.getGenre())) {
            book.setGenre(GENRE_SERVICE.get(book.getGenre()));
        } else {
            Genre newGenre = GENRE_REPOSITORY.add(book.getGenre());
            book.setGenre(newGenre);
        }

        BOOK_REPOSITORY.add(book);
        System.out.println("The book has been added successfully to the Library.");
    }

    public void update(Book book) {

        if (isPresentId(book.getId())) {
            if (AUTHOR_SERVICE.isPresent(book.getAuthor())) {
                book.setAuthor(AUTHOR_SERVICE.get(book.getAuthor()));
            } else {
                Author newAuthor = AUTHOR_REPOSITORY.add(book.getAuthor());
                book.setAuthor(newAuthor);
            }

            if (GENRE_SERVICE.isPresent(book.getGenre())) {
                book.setGenre(GENRE_SERVICE.get(book.getGenre()));
            } else {
                Genre newGenre = GENRE_REPOSITORY.add(book.getGenre());
                book.setGenre(newGenre);
            }

            BOOK_REPOSITORY.update(book);
            System.out.println("The book has been updated successfully.");
        } else {
            System.out.println("The entered id '" + book.getId() + "' doesn't exist!");
        }

    }

    public void delete(int id) {
        if (isPresentId(id)) {
            BOOK_REPOSITORY.delete(id);
            System.out.println("The book has been deleted successfully from the Library.");
        } else {
            System.out.println("The entered id '" + id + "' doesn't exist!");
        }

    }

    public Book get(int id) {
        Book book = new Book();
        if (isPresentId(id)) {
            book = BOOK_REPOSITORY.getById(id);
        } else {
            System.out.println("The entered id '" + id + "' doesn't exist!");
        }
        return book;
    }

    private boolean isPresentTitle(String title) {
        if (BOOK_REPOSITORY.getByName(title).getTitle() != null) {
            return true;
        }
        return false;
    }

    private boolean isPresentId(int id) {
        if (BOOK_REPOSITORY.getById(id).getId() != 0) {
            return true;
        }
        return false;
    }
}
