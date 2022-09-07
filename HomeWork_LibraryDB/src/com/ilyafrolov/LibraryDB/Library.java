package com.ilyafrolov.LibraryDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Library {

    private final Map<String, ArrayList<Book>> books = new TreeMap();

    public Library() {
    }

    public Map<String, ArrayList<Book>> getLibrary() {
        try {
            ResultSet rs = ConnectionToDB.postman.executeQuery("select * from books");
            while (rs.next()) {
                /*I get 'id' from DB and give to Book constructor. Is it right?*/
                Book book = new Book(rs.getInt(1), rs.getString(2),
                        Author.getAuthorByID(rs.getInt(3)), Genre.getGenreByID(rs.getInt(4)));

                if (books.containsKey(book.getTitle())) {
                    books.get(book.getTitle()).add(book);
                } else {
                    ArrayList<Book> list = new ArrayList();
                    list.add(book);
                    books.put(book.getTitle(), list);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return books;
        }
    }

    public void addBook(String title, String author, String genre) {
        try {
//            if (books.containsKey(book.getTitle())) {
//                books.get(book.getTitle()).add(book);
//            } else {
//                ArrayList<Book> list = new ArrayList();
//                list.add(book);
//                books.put(book.getTitle(), list);
//            }
            Book book = new Book();
            book.setTitle(title);//Add verification if such title is already exist
            book.setAuthor(new Author(author));//Add verification if such author is already exist
            book.setGenre(new Genre(genre));//Add verification if such genre is already exist

            PreparedStatement ps = ConnectionToDB.con.prepareStatement("insert into books (title, author_id, genre_id) values (?, ?, ?)");
            ps.setString(1, book.getTitle());

            PreparedStatement psAuthor = ConnectionToDB.con.prepareStatement("insert into authors (name) values (?)", Statement.RETURN_GENERATED_KEYS);
            psAuthor.setString(1, book.getAuthor().getAuthor());
            psAuthor.executeUpdate();
            ResultSet resSetAuthor = psAuthor.getGeneratedKeys();
            if (resSetAuthor.next()) {
                book.getAuthor().setId(resSetAuthor.getInt(1));
            }
            ps.setInt(2, book.getAuthor().getId());

            PreparedStatement psGenre = ConnectionToDB.con.prepareStatement("insert into genres (name) values (?)", Statement.RETURN_GENERATED_KEYS);
            psGenre.setString(1, book.getGenre().getGenre());
            psGenre.executeUpdate();
            ResultSet resSetGenre = psGenre.getGeneratedKeys();
            if (resSetGenre.next()) {
                book.getGenre().setId(resSetGenre.getInt(1));
            }
            ps.setInt(3, book.getGenre().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //    public Book getBook(int id) {
//    public void findBook(int id) { //add Exception if id does not consist
//        for (ArrayList<Book> list : books.values()) {
//            for (int i = 0; i < list.size(); i++) {
//                if (list.get(i).getId() == id) {
//                    System.out.println(list.get(i));
//                }
//            }
//        }
//    }
//
//    public void findBook(String title) {
//        if (books.containsKey(title)) {
//            System.out.println(books.get(title));
//        }
//    }

    public void editBook(int id, String newTitle, String newAuthor, String newGenre) { //add Exception if id does not consist
        try {
            PreparedStatement ps = ConnectionToDB.con.prepareStatement("update books set title = ?, author_id = ?, genre_id = ? where id = ?");

            ps.setString(1, newTitle);

            for (ArrayList<Book> list : books.values()) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getAuthor().getAuthor().equals(newAuthor)) {
                        ps.setInt(2, list.get(i).getAuthor().getId());
                    } else {
                        PreparedStatement psAuthor = ConnectionToDB.con.prepareStatement("insert into authors (name) values (?)", Statement.RETURN_GENERATED_KEYS);
                        psAuthor.setString(1, newAuthor);
                        psAuthor.executeUpdate();
                        ResultSet resSetAuthor = psAuthor.getGeneratedKeys();
                        Book book = new Book();
                        if (resSetAuthor.next()) {
                            for (ArrayList<Book> list2 : books.values()) {
                                for (int j = 0; j < list2.size(); j++) {
                                    if (list2.get(j).getId() == id) {
                                        book = list2.get(j);
                                        book.getAuthor().setId(resSetAuthor.getInt(1));
                                    }
                                }
                            }
                        }
                        ps.setInt(2, book.getAuthor().getId());
                    }

                    if (list.get(i).getGenre().getGenre().equals(newGenre)) {
                        ps.setInt(3, list.get(i).getGenre().getId());
                    } else {
                        PreparedStatement psGenre = ConnectionToDB.con.prepareStatement("insert into genres (name) values (?)", Statement.RETURN_GENERATED_KEYS);
                        psGenre.setString(1, newGenre);
                        psGenre.executeUpdate();
                        ResultSet resSetGenre = psGenre.getGeneratedKeys();
                        Book book = new Book();
                        if (resSetGenre.next()) {
                            for (ArrayList<Book> list2 : books.values()) {
                                for (int j = 0; j < list2.size(); j++) {
                                    if (list2.get(j).getId() == id) {
                                        book = list2.get(j);
                                        book.getGenre().setId(resSetGenre.getInt(1));
                                    }
                                }
                            }
                        }
                        ps.setInt(3, book.getGenre().getId());
                    }
                }
            }

            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    @Override
    public String toString() {
        return books.values().toString();
    }

    public void printListOfBooks() {
        Iterator<ArrayList<Book>> iterator = books.values().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}
