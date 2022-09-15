package com.ilyafrolov.LibraryDB.database.Util;

public class SQLQuery {

    public static final String GET_ALL_BOOKS = "select * from books";
    public static final String INSERT_IN_BOOKS = "insert into books (title, author_id, genre_id) values (?, ?, ?)";
    public static final String UPDATE_BOOKS = "update books set title = ?, author_id = ?, genre_id = ? where id = ?";
    public static final String DELETE_FROM_BOOKS = "delete from books where id = ?";
    public static final String GET_BOOK_BY_ID = "select * from books where id = ?";
    public static final String INSERT_IN_AUTHORS = "insert into authors (name) values (?)";
    public static final String INSERT_IN_GENRES = "insert into genres (name) values (?)";
    public static final String GET_FROM_AUTHORS_BY_ID = "select * from authors where id = ?";
    public static final String GET_FROM_GENRES_BY_ID = "select * from genres where id = ?";
    public static final String GET_AUTHOR_ID_BY_NAME = "select id from authors where name = ?";
    public static final String GET_GENRE_ID_BY_NAME = "select id from genres where name = ?";

}
