package com.ilyafrolov.LibraryDB.DataSource;

import com.ilyafrolov.LibraryDB.Pojo.Author;

import java.sql.ResultSet;

public interface AuthorRepository {

    public Author getAuthorByID(int id);

    public ResultSet setAuthorInDB(String authorName);

    public ResultSet getAuthorIdByName (String authorName);
}
