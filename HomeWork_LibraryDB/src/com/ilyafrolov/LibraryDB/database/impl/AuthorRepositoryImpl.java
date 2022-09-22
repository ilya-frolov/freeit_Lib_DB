package com.ilyafrolov.LibraryDB.database.impl;

import com.ilyafrolov.LibraryDB.database.AuthorRepository;
import com.ilyafrolov.LibraryDB.database.Util.ConnectionToDB;
import com.ilyafrolov.LibraryDB.database.Util.SQLQuery;
import com.ilyafrolov.LibraryDB.businesslogic.entity.Author;

import java.sql.*;

public final class AuthorRepositoryImpl implements AuthorRepository {

    public AuthorRepositoryImpl() {
    }

    public Author getByID(int id) {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        Author newAuthor = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_FROM_AUTHORS_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                newAuthor = new Author(rs.getInt("id"), rs.getString("name"));
            }
            closePS(ps);
            closeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return newAuthor;
    }

    public Author getByName(Author author) {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        Author newAuthor = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_FROM_AUTHORS_BY_NAME);
            ps.setString(1, author.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                newAuthor = new Author(rs.getInt("id"), rs.getString("name"));
            }
            closePS(ps);
            closeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return newAuthor;
    }

    public Author add(Author author) {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.INSERT_IN_AUTHORS, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, author.getName());
            int updatedRows = ps.executeUpdate();
            System.out.println(updatedRows + " rows were updated in 'authors'.");
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                author.setId(rs.getInt(1));
            }
            closePS(ps);
            closeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return author;
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

