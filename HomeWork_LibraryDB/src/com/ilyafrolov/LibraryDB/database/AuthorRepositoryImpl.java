package com.ilyafrolov.LibraryDB.database;

import com.ilyafrolov.LibraryDB.database.Util.ConnectionToDB;
import com.ilyafrolov.LibraryDB.database.Util.SQLQuery;
import com.ilyafrolov.LibraryDB.businesslogic.entity.Author;

import java.sql.*;

public final class AuthorRepositoryImpl implements AuthorRepository {

    public Author getByID(int id) {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        Author newAuthor = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_FROM_AUTHORS_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                newAuthor = new Author(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return newAuthor;
    }

    public Author setInDB(Author author) {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.INSERT_IN_AUTHORS, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, author.getName());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                author.setId(rs.getInt(1));
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("The author '" + author.getName() + "' already exists.");
            try {
                PreparedStatement ps = con.prepareStatement(SQLQuery.GET_AUTHOR_ID_BY_NAME);
                ps.setString(1, author.getName());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    author.setId(rs.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return author;
    }

}
