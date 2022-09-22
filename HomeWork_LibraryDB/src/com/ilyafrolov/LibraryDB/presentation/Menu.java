package com.ilyafrolov.LibraryDB.presentation;

import com.ilyafrolov.LibraryDB.businesslogic.entity.Author;
import com.ilyafrolov.LibraryDB.businesslogic.entity.Book;
import com.ilyafrolov.LibraryDB.businesslogic.entity.Genre;
import com.ilyafrolov.LibraryDB.businesslogic.service.impl.BookServiceImpl;

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

        do {
            System.out.println(STANDARD_SELECT);
            System.out.println(ACTION_LIST);

            try {
                Scanner scanner = new Scanner(System.in);
                int input = scanner.nextInt();
                Scanner scanner2 = new Scanner(System.in);

                switch (input) {
                    case 1:
                        System.out.println(services.getAll());
                        break;
                    case 2:
                        System.out.println(INPUT_TITLE);
                        String title = scanner2.nextLine();

                        System.out.println(INPUT_AUTHOR);
                        String newAuthor = scanner2.nextLine();

                        System.out.println(INPUT_GENRE);
                        String newGenre = scanner2.nextLine();

                        Author author = new Author(newAuthor);
                        Genre genre = new Genre(newGenre);
                        Book newBook = new Book(title, author, genre);
                        services.add(newBook);
                        break;
                    case 3:
                        System.out.println(INPUT_ID);
                        int id = scanner2.nextInt();

                        System.out.println(INPUT_TITLE);
                        String title2 = scanner2.nextLine();

                        System.out.println(INPUT_AUTHOR);
                        String newAuthor2 = scanner2.nextLine();

                        System.out.println(INPUT_GENRE);
                        String newGenre2 = scanner2.nextLine();

                        Author author2 = new Author(newAuthor2);
                        Genre genre2 = new Genre(newGenre2);
                        Book newBook2 = new Book(id, title2, author2, genre2);
                        services.update(newBook2);
                        break;
                    case 4:
                        System.out.println(INPUT_ID);
                        int id2 = scanner2.nextInt();

                        services.delete(id2);
                        break;
                    case 5:
                        System.out.println(INPUT_ID);
                        int id3 = scanner2.nextInt();

                        System.out.println(services.get(id3));
                        break;
                    case 6:
                        System.out.println("Thank you for using our Library. See you.\n");
                        System.out.println("The program is completed successfully.");
                        return;
                    default:
                        System.out.println("The entered action does not exist. Please, select another one.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("The wrong value is entered. Please, select the right one from the list.\n");
            }
        } while (true);
    }
}
