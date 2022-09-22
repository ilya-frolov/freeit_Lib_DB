package com.ilyafrolov.LibraryDB.database.impl;

import com.ilyafrolov.LibraryDB.database.GenreRepository;
import com.ilyafrolov.LibraryDB.database.Util.ConnectionToDB;
import com.ilyafrolov.LibraryDB.businesslogic.entity.Genre;
import com.ilyafrolov.LibraryDB.database.Util.SQLQuery;

import java.sql.*;

public final class GenreRepositoryImpl implements GenreRepository {

    public GenreRepositoryImpl() {
    }

    public Genre getByID(int id) {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        Genre newGenre = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_FROM_GENRES_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                newGenre = new Genre(rs.getInt("id"), rs.getString("name"));
            }
            closePS(ps);
            closeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return newGenre;
    }

    public Genre getByName(Genre genre) {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        Genre newGenre = null;
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.GET_FROM_GENRES_BY_NAME);
            ps.setString(1, genre.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                newGenre = new Genre(rs.getInt("id"), rs.getString("name"));
            }
            closePS(ps);
            closeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return newGenre;
    }

    public Genre add(Genre genre) {
        Connection con = ConnectionToDB.connectionPool.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQLQuery.INSERT_IN_GENRES, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, genre.getName());
            int updatedRows = ps.executeUpdate();
            System.out.println(updatedRows + " rows were updated in 'genres'.");
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                genre.setId(rs.getInt(1));
            }
            closePS(ps);
            closeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionToDB.connectionPool.releaseConnection(con);
        }
        return genre;
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
