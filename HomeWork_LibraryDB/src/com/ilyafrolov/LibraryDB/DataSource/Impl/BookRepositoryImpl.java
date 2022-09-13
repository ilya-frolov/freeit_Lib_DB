package com.ilyafrolov.LibraryDB.DataSource.Impl;

import com.ilyafrolov.LibraryDB.DataSource.Util.ConnectionToDB;
import com.ilyafrolov.LibraryDB.DataSource.BookRepository;
import com.ilyafrolov.LibraryDB.Pojo.Book;
import com.ilyafrolov.LibraryDB.DataSource.RepositorySupplier;
import com.ilyafrolov.LibraryDB.DataSource.Util.SQLQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class BookRepositoryImpl implements BookRepository {

    private Connection con;

    public List<Book> getAllBooks() {
        con = ConnectionToDB.connectionPool.getConnection();
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_ALL_BOOKS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book newBook = new Book(rs.getInt(1), rs.getString(2),
                        RepositorySupplier.getAuthorRepository().getAuthorByID(rs.getInt(3)),
                        RepositorySupplier.getGenreRepository().getGenreByID(rs.getInt(4)));
                books.add(newBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return books;
    }


    public void insertInBooks(String title, String authorName, String genreName) {
        con = ConnectionToDB.connectionPool.getConnection();
        try {
                PreparedStatement ps = con.prepareStatement(SQLQuery.INSERT_IN_BOOKS);
                ps.setString(1, title);

            try {
                ResultSet rs = RepositorySupplier.getAuthorRepository().setAuthorInDB(authorName);
                if (rs.next()) {
                    ps.setInt(2, rs.getInt(1));
                }
            } catch (NullPointerException e) {
                ResultSet rs = RepositorySupplier.getAuthorRepository().getAuthorIdByName(authorName);
                if (rs.next()) {
                    ps.setInt(2, rs.getInt(1));
                }
            }

            try {
                ResultSet rs = RepositorySupplier.getGenreRepository().setGenreInDB(genreName);
                if (rs.next()) {
                    ps.setInt(3, rs.getInt(1));
                }
            } catch (NullPointerException e) {
                ResultSet rs = RepositorySupplier.getGenreRepository().getGenreIdByName(genreName);
                if (rs.next()) {
                    ps.setInt(3, rs.getInt(1));
                }
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
    }

    public void updateBook(int id, String newTitle, String newAuthor, String newGenre) {
        con = ConnectionToDB.connectionPool.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.UPDATE_BOOKS);
            ps.setString(1, newTitle);

            try {
                ResultSet rs = RepositorySupplier.getAuthorRepository().setAuthorInDB(newAuthor);
                if (rs.next()) {
                    ps.setInt(2, rs.getInt(1));
                }
            } catch (NullPointerException e) {
                ResultSet rs = RepositorySupplier.getAuthorRepository().getAuthorIdByName(newAuthor);
                if (rs.next()) {
                    ps.setInt(2, rs.getInt(1));
                }
            }

            try {
                ResultSet rs = RepositorySupplier.getGenreRepository().setGenreInDB(newGenre);
                if (rs.next()) {
                    ps.setInt(3, rs.getInt(1));
                }
            } catch (NullPointerException e) {
                ResultSet rs = RepositorySupplier.getGenreRepository().getGenreIdByName(newGenre);
                if (rs.next()) {
                    ps.setInt(3, rs.getInt(1));
                }
            }

            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
    }

    public void deleteBook(int id) {
        try {
            con = ConnectionToDB.connectionPool.getConnection();
            PreparedStatement ps = con.prepareStatement(SQLQuery.DELETE_FROM_BOOKS);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
    }

    public Book getBook(int id) {
        Book book = new Book();
        con = ConnectionToDB.connectionPool.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_BOOK_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                book.setId(id);
                book.setTitle(rs.getString(2));
                book.setAuthor(RepositorySupplier.getAuthorRepository().getAuthorByID(rs.getInt(3)));
                book.setGenre(RepositorySupplier.getGenreRepository().getGenreByID(rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return book;
    }

}
