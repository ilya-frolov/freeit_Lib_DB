package com.ilyafrolov.LibraryDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Author {

    private int id;
    private String author;

    public Author() {
    }

    public Author(int id, String author) {
        this.id = id;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public static Author getAuthorByID(int id) throws SQLException {
        PreparedStatement ps = ConnectionToDB.con.prepareStatement("select * from authors where id = ?");
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return new Author(rs.getInt(1), rs.getString(2));
    }

    @Override
    public String toString() {
        return author;
    }

}
