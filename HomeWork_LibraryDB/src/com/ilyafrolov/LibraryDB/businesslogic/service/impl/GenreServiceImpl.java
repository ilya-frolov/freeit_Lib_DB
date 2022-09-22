package com.ilyafrolov.LibraryDB.businesslogic.service.impl;

import com.ilyafrolov.LibraryDB.businesslogic.entity.Genre;
import com.ilyafrolov.LibraryDB.businesslogic.service.GenreService;
import com.ilyafrolov.LibraryDB.database.impl.GenreRepositoryImpl;

public class GenreServiceImpl implements GenreService {

    private static final GenreRepositoryImpl GENRE_REPOSITORY = new GenreRepositoryImpl();

    public GenreServiceImpl() {
    }

    public Genre get(Genre genre) {
        return GENRE_REPOSITORY.getByName(genre);
    }

    public boolean isPresent(Genre genre) {
        if (GENRE_REPOSITORY.getByName(genre) != null) {
            return true;
        }
        return false;
    }

}
