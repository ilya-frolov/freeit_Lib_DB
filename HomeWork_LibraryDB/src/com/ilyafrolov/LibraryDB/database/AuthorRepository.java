package com.ilyafrolov.LibraryDB.database;

import com.ilyafrolov.LibraryDB.businesslogic.entity.Author;

public interface AuthorRepository {

    Author getByID(int id);

    Author setInDB(Author author);

}
