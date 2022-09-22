package com.ilyafrolov.LibraryDB.database;

import com.ilyafrolov.LibraryDB.businesslogic.entity.Genre;

public interface GenreRepository {

    Genre getByID(int id);

    Genre getByName(Genre genre);

    Genre add(Genre genre);

}
