package com.ilyafrolov.LibraryDB.businesslogic.service.impl;

import com.ilyafrolov.LibraryDB.businesslogic.entity.Author;
import com.ilyafrolov.LibraryDB.businesslogic.service.AuthorService;
import com.ilyafrolov.LibraryDB.database.impl.AuthorRepositoryImpl;

public class AuthorServiceImpl implements AuthorService {

    private static final AuthorRepositoryImpl AUTHOR_REPOSITORY = new AuthorRepositoryImpl();

    public AuthorServiceImpl() {
    }

    public Author get(Author author) {
        return AUTHOR_REPOSITORY.getByName(author);
    }

    public boolean isPresent(Author author) {
        if (AUTHOR_REPOSITORY.getByName(author) != null) {
            return true;
        }
        return false;
    }
}
