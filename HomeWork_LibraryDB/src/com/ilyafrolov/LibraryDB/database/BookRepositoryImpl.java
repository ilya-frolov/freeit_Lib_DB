package com.ilyafrolov.LibraryDB.database;

import com.ilyafrolov.LibraryDB.businesslogic.entity.Author;
import com.ilyafrolov.LibraryDB.businesslogic.entity.Genre;
import com.ilyafrolov.LibraryDB.database.Util.ConnectionToDB;
import com.ilyafrolov.LibraryDB.businesslogic.entity.Book;
import com.ilyafrolov.LibraryDB.database.Util.SQLQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class BookRepositoryImpl implements BookRepository {

    public List<Book> getAll() {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_ALL_BOOKS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book newBook = new Book(rs.getInt(1), rs.getString(2),
                        RepositorySupplier.getAuthorRepository().getByID(rs.getInt(3)),
                        RepositorySupplier.getGenreRepository().getByID(rs.getInt(4)));
                books.add(newBook);
            }
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

            try {
                Author author = RepositorySupplier.getAuthorRepository().setInDB(book.getAuthor());
                ps.setInt(2, author.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Genre genre = RepositorySupplier.getGenreRepository().setInDB(book.getGenre());
                ps.setInt(3, genre.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            ps.executeUpdate();
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

            try {
                Author author = RepositorySupplier.getAuthorRepository().setInDB(book.getAuthor());
                ps.setInt(2, author.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Genre genre = RepositorySupplier.getGenreRepository().setInDB(book.getGenre());
                ps.setInt(3, genre.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            ps.setInt(4, book.getId());
            ps.executeUpdate();
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
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
    }

    public Book get(int id) {
        Book book = new Book();
        Connection con = ConnectionToDB.connectionPool.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_BOOK_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                book.setId(id);
                book.setTitle(rs.getString(2));
                book.setAuthor(RepositorySupplier.getAuthorRepository().getByID(rs.getInt(3)));
                book.setGenre(RepositorySupplier.getGenreRepository().getByID(rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return book;
    }

}
