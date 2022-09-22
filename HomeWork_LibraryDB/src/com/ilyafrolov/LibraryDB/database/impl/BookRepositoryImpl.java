package com.ilyafrolov.LibraryDB.database.impl;

import com.ilyafrolov.LibraryDB.businesslogic.entity.Author;
import com.ilyafrolov.LibraryDB.businesslogic.entity.Genre;
import com.ilyafrolov.LibraryDB.database.BookRepository;
import com.ilyafrolov.LibraryDB.database.Util.ConnectionToDB;
import com.ilyafrolov.LibraryDB.businesslogic.entity.Book;
import com.ilyafrolov.LibraryDB.database.Util.SQLQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class BookRepositoryImpl implements BookRepository {

    private static final AuthorRepositoryImpl AUTHOR_REPOSITORY = new AuthorRepositoryImpl();
    private static final GenreRepositoryImpl GENRE_REPOSITORY = new GenreRepositoryImpl();

    public BookRepositoryImpl() {
    }

    public List<Book> getAll() {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_ALL_BOOKS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book newBook = new Book(rs.getInt("id"), rs.getString("title"),
                        AUTHOR_REPOSITORY.getByID(rs.getInt("author_id")),
                        GENRE_REPOSITORY.getByID(rs.getInt("genre_id")));
                books.add(newBook);
            }
            closePS(ps);
            closeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return books;
    }


    public void add(Book book) {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.INSERT_IN_BOOKS);
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getAuthor().getId());
            ps.setInt(3, book.getGenre().getId());
            int updatedRows = ps.executeUpdate();
            closePS(ps);
            System.out.println(updatedRows + " rows were updated in 'books'.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
    }

    public void update(Book book) {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.UPDATE_BOOKS);
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getAuthor().getId());
            ps.setInt(3, book.getGenre().getId());
            ps.setInt(4, book.getId());
            int updatedRows = ps.executeUpdate();
            System.out.println(updatedRows + " rows were updated in 'books'.");
            closePS(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
    }

    public void delete(int id) {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.DELETE_FROM_BOOKS);
            ps.setInt(1, id);
            int updatedRows = ps.executeUpdate();
            System.out.println(updatedRows + " rows were updated in 'book'.");
            closePS(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
    }

    public Book getById(int id) {
        Book book = new Book();
        Connection con = ConnectionToDB.connectionPool.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_BOOK_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                book.setId(id);
                book.setTitle(rs.getString("title"));
                book.setAuthor(AUTHOR_REPOSITORY.getByID(rs.getInt("author_id")));
                book.setGenre(GENRE_REPOSITORY.getByID(rs.getInt("genre_id")));
            }
            closePS(ps);
            closeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return book;
    }

    public Book getByName(String title) {
        Book newBook = new Book();
        Connection con = ConnectionToDB.connectionPool.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_BOOK_BY_TITLE);
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                newBook.setId(rs.getInt("id"));
                newBook.setTitle(rs.getString("title"));
                newBook.setAuthor(AUTHOR_REPOSITORY.getByID(rs.getInt("author_id")));
                newBook.setGenre(GENRE_REPOSITORY.getByID(rs.getInt("genre_id")));
            }
            closePS(ps);
            closeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return newBook;
    }

    private static void closePS(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ignore) {
            }
        }
    }

    private static void closeRS(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ignore) {
            }
        }
    }
}
