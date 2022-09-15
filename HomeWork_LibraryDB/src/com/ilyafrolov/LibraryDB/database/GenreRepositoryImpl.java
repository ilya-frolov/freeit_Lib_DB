package com.ilyafrolov.LibraryDB.database;

import com.ilyafrolov.LibraryDB.database.Util.ConnectionToDB;
import com.ilyafrolov.LibraryDB.businesslogic.entity.Genre;
import com.ilyafrolov.LibraryDB.database.Util.SQLQuery;

import java.sql.*;

public final class GenreRepositoryImpl implements GenreRepository {

    public Genre getByID(int id) {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        Genre newGenre = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_FROM_GENRES_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                newGenre = new Genre(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return newGenre;
    }

    public Genre setInDB(Genre genre) {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.INSERT_IN_GENRES, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, genre.getName());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                genre.setId(rs.getInt(1));
            }
        }catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("The genre '" + genre.getName() + "' already exists.");
            try {
                PreparedStatement ps = con.prepareStatement(SQLQuery.GET_GENRE_ID_BY_NAME);
                ps.setString(1, genre.getName());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    genre.setId(rs.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return genre;
    }

}
