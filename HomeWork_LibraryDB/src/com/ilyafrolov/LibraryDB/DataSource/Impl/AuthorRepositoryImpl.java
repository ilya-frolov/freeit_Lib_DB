package com.ilyafrolov.LibraryDB.DataSource.Impl;

import com.ilyafrolov.LibraryDB.DataSource.Util.ConnectionToDB;
import com.ilyafrolov.LibraryDB.DataSource.AuthorRepository;
import com.ilyafrolov.LibraryDB.DataSource.Util.SQLQuery;
import com.ilyafrolov.LibraryDB.Pojo.Author;

import java.sql.*;

public final class AuthorRepositoryImpl implements AuthorRepository {

    private Connection con;

    public Author getAuthorByID(int id) {
        con = ConnectionToDB.connectionPool.getConnection();
        Author newAuthor = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_FROM_AUTHORS_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            newAuthor = new Author(rs.getInt(1), rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return newAuthor;
    }

    public ResultSet setAuthorInDB(String authorName) {
        con = ConnectionToDB.connectionPool.getConnection();
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.INSERT_IN_AUTHORS, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, authorName);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
        }catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("The author '" + authorName + "' already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return rs;
    }

    public ResultSet getAuthorIdByName(String authorName) {
        con = ConnectionToDB.connectionPool.getConnection();
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_AUTHOR_ID_BY_NAME);
            ps.setString(1, authorName);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return rs;
    }
}
