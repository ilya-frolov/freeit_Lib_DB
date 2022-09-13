package com.ilyafrolov.LibraryDB.DataSource.Impl;

import com.ilyafrolov.LibraryDB.DataSource.Util.ConnectionToDB;
import com.ilyafrolov.LibraryDB.DataSource.GenreRepository;
import com.ilyafrolov.LibraryDB.Pojo.Genre;
import com.ilyafrolov.LibraryDB.DataSource.Util.SQLQuery;

import java.sql.*;

public final class GenreRepositoryImpl implements GenreRepository {

    private Connection con;

    public Genre getGenreByID(int id) {
        con = ConnectionToDB.connectionPool.getConnection();
        Genre newGenre = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_FROM_GENRES_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            newGenre = new Genre(rs.getInt(1), rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return newGenre;
    }

    public ResultSet setGenreInDB(String genreName) {
        con = ConnectionToDB.connectionPool.getConnection();
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.INSERT_IN_GENRES, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, genreName);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
        }catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("The genre '" + genreName + "' already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return rs;
    }

    public ResultSet getGenreIdByName(String genreName) {
        con = ConnectionToDB.connectionPool.getConnection();
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_GENRE_ID_BY_NAME);
            ps.setString(1, genreName);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return rs;
    }
}
