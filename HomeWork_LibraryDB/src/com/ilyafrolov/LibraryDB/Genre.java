package com.ilyafrolov.LibraryDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Genre {

    private int id;
    private String genre;

    public Genre() {
    }

    public Genre(int id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public static Genre getGenreByID(int id) throws SQLException {
        PreparedStatement ps = ConnectionToDB.con.prepareStatement("select * from genres where id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return new Genre(rs.getInt(1), rs.getString(2));
    }

    @Override
    public String toString() {
        return genre;
    }
}
