package com.ilyafrolov.LibraryDB.presentation;

import com.ilyafrolov.LibraryDB.businesslogic.entity.Author;
import com.ilyafrolov.LibraryDB.businesslogic.entity.Book;
import com.ilyafrolov.LibraryDB.businesslogic.entity.Genre;
import com.ilyafrolov.LibraryDB.businesslogic.service.BookServiceImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class Menu {

    private static final String STANDARD_SELECT = "\nPlease, select the number of the required action:";
    private static final String ACTION_LIST = "1. Show all books;\n2. Add a new book to the Library;\n" +
            "3. Edit the book from the Library;\n4. Delete the book from the Library;\n" +
            "5. Find the book in the Library.\n6. Leave the Library.";
    private static final String INPUT_TITLE = "Please, enter the title of the book:";
    private static final String INPUT_AUTHOR = "Please, enter the author of the book:";
    private static final String INPUT_GENRE = "Please, enter the genre of the book:";
    private static final String INPUT_ID = "Please, enter the id of the book:";

    public static void launchApp() {

        BookServiceImpl services = new BookServiceImpl();
        System.out.println("Welcome to the Library.");

        boolean action = true;
        do {
            System.out.println(STANDARD_SELECT);
            System.out.println(ACTION_LIST);

            try {
                Scanner scanner = new Scanner(System.in);
                int input = scanner.nextInt();

                switch (input) {
                    case 1:
                        System.out.println(services.getAll());
                        break;
                    case 2:
                        System.out.println(INPUT_TITLE);
                        Scanner scanner2 = new Scanner(System.in);
                        String title = scanner2.nextLine();

                        System.out.println(INPUT_AUTHOR);
                        Scanner scanner3 = new Scanner(System.in);
                        String newAuthor = scanner3.nextLine();

                        System.out.println(INPUT_GENRE);
                        Scanner scanner4 = new Scanner(System.in);
                        String newGenre = scanner4.nextLine();

                        Author author = new Author(newAuthor);
                        Genre genre = new Genre(newGenre);
                        Book newBook = new Book(title, author, genre);
                        services.add(newBook);
                        break;
                    case 3:
                        System.out.println(INPUT_ID);
                        Scanner scanner5 = new Scanner(System.in);
                        int id = scanner5.nextInt();

                        System.out.println(INPUT_TITLE);
                        Scanner scanner6 = new Scanner(System.in);
                        String title2 = scanner6.nextLine();

                        System.out.println(INPUT_AUTHOR);
                        Scanner scanner7 = new Scanner(System.in);
                        String newAuthor2 = scanner7.nextLine();

                        System.out.println(INPUT_GENRE);
                        Scanner scanner8 = new Scanner(System.in);
                        String newGenre2 = scanner8.nextLine();

                        Author author2 = new Author(newAuthor2);
                        Genre genre2 = new Genre(newGenre2);
                        Book newBook2 = new Book(id, title2, author2, genre2);
                        services.update(newBook2);
                        break;
                    case 4:
                        System.out.println(INPUT_ID);
                        Scanner scanner9 = new Scanner(System.in);
                        int id2 = scanner9.nextInt();

                        services.delete(id2);
                        break;
                    case 5:
                        System.out.println(INPUT_ID);
                        Scanner scanner10 = new Scanner(System.in);
                        int id3 = scanner10.nextInt();

                        System.out.println(services.get(id3));
                        break;
                    case 6:
                        action = false;
                        System.out.println("Thank you for using our Library. See you.\n");
                        break;
                    default:
                        System.out.println("The entered action does not exist. Please, select another one.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("The wrong value is entered. Please, select the right one from the list.\n");
            }
        } while (action);
        System.out.println("The program is completed successfully.");
    }
}
