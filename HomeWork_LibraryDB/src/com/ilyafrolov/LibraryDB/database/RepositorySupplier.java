package com.ilyafrolov.LibraryDB.database;

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
