package com.ilyafrolov.LibraryDB.businesslogic.entity;

import java.util.Objects;

public class Book {

    private int id;
    private String title;
    private Author author;
    private Genre genre;


    public Book() {
    }

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }
    public Book(int id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public Author getAuthor(){
        return author;
    }

    public void setAuthor(Author author){
        this.author = author;
    }

    public Genre getGenre(){
        return genre;
    }

    public void setGenre(Genre genre){
        this.genre = genre;
    }

    @Override
    public String toString(){
        return "Book id = " + id +
                ", title: '" + title +
                "', author: '" + author +
                "', genre: '" + genre +
                "'";
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash + id;
        hash = 31 * hash + (title == null ? 0 : title.hashCode());
        hash = 31 * hash + (author.getName() == null ? 0 : author.getName().hashCode());
        hash = 31 * hash + (genre.getName() == null ? 0 : genre.getName().hashCode());
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
        Book book = (Book) o;
        return id == book.id && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre);
    }

}
