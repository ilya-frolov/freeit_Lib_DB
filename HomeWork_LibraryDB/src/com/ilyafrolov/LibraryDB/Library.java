//package com.ilyafrolov.LibraryDB;
//
//import java.sql.*;
//import java.util.*;
//
//public class Library {
//
//    private static final PreparedStatement psGetAllBooks, psInsertInBooks, psUpdateBooks, psDeleteFromBooks, psGetBookById;
//    private static final PreparedStatement psInsertInAuthors, psGetAllAuthors, psGetAuthorIdByName;
//    private static final PreparedStatement psInsertInGenres, psGetAllGenres, psGetGenreIdByName;
//
//    static {
//        try {
//            psGetAllBooks = ConnectionToDB.con.prepareStatement("select * from books");
//            psInsertInBooks = ConnectionToDB.con.prepareStatement("insert into books (title, author_id, genre_id) values (?, ?, ?)");
//            psUpdateBooks = ConnectionToDB.con.prepareStatement("update books set title = ?, author_id = ?, genre_id = ? where id = ?");
//            psDeleteFromBooks = ConnectionToDB.con.prepareStatement("delete from books where id = ?");
//            psGetBookById = ConnectionToDB.con.prepareStatement("select * from books where id = ?");
//            psInsertInAuthors = ConnectionToDB.con.prepareStatement("insert into authors (name) values (?)", Statement.RETURN_GENERATED_KEYS);
//            psInsertInGenres = ConnectionToDB.con.prepareStatement("insert into genres (name) values (?)", Statement.RETURN_GENERATED_KEYS);
//            psGetAllAuthors = ConnectionToDB.con.prepareStatement("select * from authors where id = ?");
//            psGetAllGenres = ConnectionToDB.con.prepareStatement("select * from genres where id = ?");
//            psGetAuthorIdByName = ConnectionToDB.con.prepareStatement("select id from authors where name = ?");
//            psGetGenreIdByName = ConnectionToDB.con.prepareStatement("select id from genres where name = ?");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Library() {
//    }
//
//    public static List<Book> getAllBooks() {
//        List<Book> books = new ArrayList<>();
//        try {
//            PreparedStatement ps = ConnectionToDB.con.prepareStatement(SQLQuery.GET_ALL_BOOKS);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Book newBook = new Book(rs.getInt(1), rs.getString(2),
//                        getAuthorByID(rs.getInt(3)), getGenreByID(rs.getInt(4)));
//                books.add(newBook);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return books;
//    }
//
//    public void addBook(String title, String authorName, String genreName) {
//        try {
//            for (Book bookFromDB : getAllBooks()) {
//                if (bookFromDB.getTitle().equals(title)) {
//                    System.out.println("A book with the title " + title + " already exists:");
//                    System.out.print(bookFromDB + "\n");
//                }
//            }
//
//            psInsertInBooks.setString(1, title);
//
//            try {
//                ResultSet rs = setAuthorInDB(authorName);
//                if (rs.next()) {
//                    psInsertInBooks.setInt(2, rs.getInt(1));
//                }
//            } catch (SQLIntegrityConstraintViolationException e) {
//                System.out.println("The author '" + authorName + "' already exists.");
//                psGetAuthorIdByName.setString(1, authorName);
//                ResultSet rs = psGetAuthorIdByName.executeQuery();
//                if (rs.next()) {
//                    psInsertInBooks.setInt(2, rs.getInt(1));
//                }
//            }
//
//            try {
//                ResultSet rs = setGenreInDB(genreName);
//                if (rs.next()) {
//                    psInsertInBooks.setInt(3, rs.getInt(1));
//                }
//            } catch (SQLIntegrityConstraintViolationException e) {
//                System.out.println("The genre '" + genreName + "' already exists.");
//                psGetGenreIdByName.setString(1, genreName);
//                ResultSet rs = psGetGenreIdByName.executeQuery();
//                if (rs.next()) {
//                    psInsertInBooks.setInt(3, rs.getInt(1));
//                }
//            }
//
//            psInsertInBooks.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void editBook(int id, String newTitle, String newAuthor, String newGenre) {
//        try {
//            boolean idExist = false;
//            for (Book bookFromDB : getAllBooks()) {
//                if (bookFromDB.getId() == id) {
//                    idExist = true;
//                }
//            }
//
//            if (idExist) {
//                psUpdateBooks.setString(1, newTitle);
//
//                try {
//                    ResultSet rs = setAuthorInDB(newAuthor);
//                    if (rs.next()) {
//                        psUpdateBooks.setInt(2, rs.getInt(1));
//                    }
//                } catch (SQLIntegrityConstraintViolationException e) {
//                    psGetAuthorIdByName.setString(1, newAuthor);
//                    ResultSet rs = psGetAuthorIdByName.executeQuery();
//                    if (rs.next()) {
//                        psUpdateBooks.setInt(2, rs.getInt(1));
//                    }
//                }
//
//                try {
//                    ResultSet rs = setGenreInDB(newGenre);
//                    if (rs.next()) {
//                        psUpdateBooks.setInt(3, rs.getInt(1));
//                    }
//                } catch (SQLIntegrityConstraintViolationException e) {
//                    psGetGenreIdByName.setString(1, newGenre);
//                    ResultSet rs = psGetGenreIdByName.executeQuery();
//                    if (rs.next()) {
//                        psUpdateBooks.setInt(3, rs.getInt(1));
//                    }
//                }
//
//                psUpdateBooks.setInt(4, id);
//                psUpdateBooks.executeUpdate();
//            } else {
//                System.out.println("The entered id '" + id + "' doesn't exist!");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteBook(int id) {
//        boolean idExist = false;
//        for (Book bookFromDB : getAllBooks()) {
//            if (bookFromDB.getId() == id) {
//                idExist = true;
//            }
//        }
//
//        if (idExist) {
//            try {
//                psDeleteFromBooks.setInt(1, id);
//                psDeleteFromBooks.executeUpdate();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("The ID '" + id + "' doesn't exist.");
//        }
//    }
//
//    public Book getBook(int id) {
//        boolean idExist = false;
//        for (Book bookFromDB : getAllBooks()) {
//            if (bookFromDB.getId() == id) {
//                idExist = true;
//            }
//        }
//
//        Book book = new Book();
//        if (idExist) {
//            try {
//                psGetBookById.setInt(1, id);
//                ResultSet rs = psGetBookById.executeQuery();
//                while (rs.next()) {
//                    book.setId(id);
//                    book.setTitle(rs.getString(2));
//                    book.setAuthor(getAuthorByID(rs.getInt(3)));
//                    book.setGenre(getGenreByID(rs.getInt(4)));
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("The ID '" + id + "' doesn't exist.");
//        }
//        return book;
//    }
//
//    public static Author getAuthorByID(int id) throws SQLException {
//        psGetAllAuthors.setInt(1, id);
//        ResultSet rs = psGetAllAuthors.executeQuery();
//        rs.next();
//        return new Author(rs.getInt(1), rs.getString(2));
//    }
//
//    public static Genre getGenreByID(int id) throws SQLException {
//        psGetAllGenres.setInt(1, id);
//        ResultSet rs = psGetAllGenres.executeQuery();
//        rs.next();
//        return new Genre(rs.getInt(1), rs.getString(2));
//    }
//
//    public static ResultSet setAuthorInDB(String authorName) throws SQLException {
//        psInsertInAuthors.setString(1, authorName);
//        psInsertInAuthors.executeUpdate();
//        return psInsertInAuthors.getGeneratedKeys();
//    }
//
//    public static ResultSet setGenreInDB(String genreName) throws SQLException {
//        psInsertInGenres.setString(1, genreName);
//        psInsertInGenres.executeUpdate();
//        return psInsertInGenres.getGeneratedKeys();
//    }
//
//}
