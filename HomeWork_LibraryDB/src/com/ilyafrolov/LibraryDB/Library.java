package com.ilyafrolov.LibraryDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Library {

    private final Map<String, ArrayList<Book>> books = new TreeMap();

    public Library() {
    }

    public Map<String, ArrayList<Book>> getLibrary() {
        try {
            ResultSet rs = ConnectionToDB.postman.executeQuery("select * from books");
//        Map<String, ArrayList<Book>> books = new TreeMap();

            while (rs.next()) {
                Book book = new Book(rs.getInt(1), rs.getString(2),
                        Author.getAuthorByID(3),
                        Genre.getGenreByID(4));
                ArrayList<Book> list = new ArrayList();
                list.add(book);
                books.put(book.getTitle(), list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return books; //Create toString()
        }
    }

    public void addBook(Book book) {
        try {
            if (books.containsKey(book.getTitle())) {
                books.get(book.getTitle()).add(book);
            } else {
                ArrayList<Book> list = new ArrayList();
                list.add(book);
                books.put(book.getTitle(), list);
            }
            PreparedStatement ps = ConnectionToDB.con.prepareStatement("insert into books (title, author_id, genre_id) values (?, ?, ?)");
            ps.setString(2, book.getTitle());

            PreparedStatement psAuthor = ConnectionToDB.con.prepareStatement("insert into authors (name) values (?)", Statement.RETURN_GENERATED_KEYS);
            psAuthor.setString(1, book.getAuthor().getAuthor());
            psAuthor.executeUpdate();
            ResultSet resSetAuthor = psAuthor.getGeneratedKeys();
            if (resSetAuthor.next()) {
                book.getAuthor().setId(resSetAuthor.getInt(1));
            }
            ps.setInt(3, book.getAuthor().getId());
            ps.executeUpdate();

            PreparedStatement psGenre = ConnectionToDB.con.prepareStatement("insert into genres (name) values (?)", Statement.RETURN_GENERATED_KEYS);
            psGenre.setString(1, book.getGenre().getGenre());
            psGenre.executeUpdate();
            ResultSet resSetGenre = psGenre.getGeneratedKeys();
            if (resSetGenre.next()) {
                book.getGenre().setId(resSetGenre.getInt(1));
            }
            ps.setInt(4, book.getGenre().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //    public Book getBook(int id) {
    public void findBook(int id) { //add Exception if id does not consist
        for (ArrayList<Book> list : books.values()) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == id) {
                    System.out.println(list.get(i));
                }
            }
        }
    }

    public void findBook(String title) {
        if (books.containsKey(title)) {
            System.out.println(books.get(title));
        }
    }

    public Book editBook(int id, Book book) { //add Exception if id does not consist
        Book newBook = new Book();
        for (ArrayList<Book> list : books.values()) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == id) {
                    list.get(i).setTitle(book.getTitle());
                    list.get(i).setAuthor(book.getAuthor());
                    list.get(i).setGenre(book.getGenre());
                    newBook = list.get(i);
                }
            }
        }
        return newBook;
    }

    public void deleteBook(int id) {
        try {
            PreparedStatement ps = ConnectionToDB.con.prepareStatement("delete from books where id = ?");
            for (ArrayList<Book> list : books.values()) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId() == (id)) {
                        ps.setInt(1, list.get(i).getId());
                        ps.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public String toString(){
//        return
//    }

        public void printListOfBooks () {
            Iterator<ArrayList<Book>> iterator = books.values().iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        }

    }
