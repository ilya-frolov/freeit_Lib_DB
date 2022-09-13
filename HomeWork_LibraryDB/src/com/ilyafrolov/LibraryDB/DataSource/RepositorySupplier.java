package com.ilyafrolov.LibraryDB.DataSource;

import com.ilyafrolov.LibraryDB.DataSource.Impl.AuthorRepositoryImpl;
import com.ilyafrolov.LibraryDB.DataSource.Impl.BookRepositoryImpl;
import com.ilyafrolov.LibraryDB.DataSource.Impl.GenreRepositoryImpl;

public class RepositorySupplier {
    private static BookRepositoryImpl bookRepository = new BookRepositoryImpl();
    private static AuthorRepositoryImpl authorRepository = new AuthorRepositoryImpl();
    private static GenreRepositoryImpl genreRepository = new GenreRepositoryImpl();

    public static BookRepositoryImpl getBookRepository() {
        return bookRepository;
    }

    public static AuthorRepositoryImpl getAuthorRepository() {
        return authorRepository;
    }

    public static GenreRepositoryImpl getGenreRepository() {
        return genreRepository;
    }
}
