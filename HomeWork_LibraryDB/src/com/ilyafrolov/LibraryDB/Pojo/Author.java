package com.ilyafrolov.LibraryDB.Pojo;

import java.util.Objects;

public class Author {

    private int id;
    private String authorName;

    public Author() {
    }

    public Author(int id, String authorName) {
        this.id = id;
        this.authorName = authorName;
    }

    public Author(String authorName) {
        this.authorName = authorName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return authorName;
    }

    public void setAuthor(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return authorName;
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash + id;
        hash = 31 * hash + (authorName == null ? 0 : authorName.hashCode());
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
        Author author = (Author) o;
        return id == author.id && Objects.equals(author, author.authorName);
    }
}
