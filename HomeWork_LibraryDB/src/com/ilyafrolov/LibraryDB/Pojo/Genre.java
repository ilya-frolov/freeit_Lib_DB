package com.ilyafrolov.LibraryDB.Pojo;

import java.util.Objects;

public class Genre {

    private int id;
    private String genreName;

    public Genre() {
    }

    public Genre(int id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genreName;
    }

    public void setGenre(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return genreName;
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash + id;
        hash = 31 * hash + (genreName == null ? 0 : genreName.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Genre genre = (Genre) o;
        return id == genre.id && Objects.equals(genre, genre.genreName);
    }
}
