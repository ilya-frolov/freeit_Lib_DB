package com.ilyafrolov.LibraryDB.DataSource;

import com.ilyafrolov.LibraryDB.Pojo.Genre;

import java.sql.ResultSet;

public interface GenreRepository {

    public Genre getGenreByID(int id);

    public ResultSet setGenreInDB(String genreName);

    public ResultSet getGenreIdByName (String genreName);
}
