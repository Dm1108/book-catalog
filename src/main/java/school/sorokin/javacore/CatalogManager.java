package school.sorokin.javacore;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CatalogManager {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Book> catalog = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(CatalogManager.class.getName());
    private static final String INPUT_BOOK_TITLE = "Введите название книги: ";
    private static final String NO_BOOK_IN_CATALOG = "Нет такой книги в каталоге";
    private static final String INPUT_AUTHOR = "Введите автора: ";

    public void bookMenu() {
        int pointOfMenu;
        while (true) {
            System.out.println("""
                    Выберите пункт меню:
                    1. Вывести каталог.
                    2. Добавить объект.
                    3. Выдать объект.
                    4. Вернуть объект.
                    5. Выйти из приложения.
                    
                    Введите цифру от 1 до 5:
                    """);
            while (true) {
                try {
                    pointOfMenu = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    logger.log(Level.SEVERE, "Вы ввели не число. Повторите ввод числа от 1 до 5");
                }
            }
            switch (pointOfMenu) {
                case 1 -> System.out.println(getAllBooks());
                case 2 -> addBook();
                case 3 -> takeBook();
                case 4 -> returnBook();
                case 5 -> {
                    System.out.println("Выход из приложения: ");
                    System.exit(0);
                }
            }
        }
    }

    public void addBook() {
        String title = inputBookTitle();
        String author = inputAuthor();
        int availableCopies = inputAvailableCopies();

        for (Book book : catalog) {
            if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) {
                book.setAvailableCopies(book.getAvailableCopies() + availableCopies);
                return;
            }
        }
        catalog.add(new Book(title, author, availableCopies));
    }

    public void takeBook() throws NoAvailableCopiesException, NoSuchElementException {
        String title = inputBookTitle();
        for (Book ex : catalog) {
            if (ex.getTitle().equalsIgnoreCase(title)) {
                int takeBookNumber = inputAvailableCopies();
                if (ex.getAvailableCopies() >= takeBookNumber) {
                    ex.setAvailableCopies(ex.getAvailableCopies() - takeBookNumber);
                    return;
                } else if (ex.getAvailableCopies() == 0) {
                    throw new NoAvailableCopiesException("Сейчас нет экземпляра этой книги в каталоге. Она выдана");
                } else if (ex.getAvailableCopies() < takeBookNumber) {
                    throw new NoSuchElementException("В каталоге нет столько экземпляров книги " + ex.getTitle());
                }
            }
        }
        throw new NoSuchElementException(NO_BOOK_IN_CATALOG);
    }

    public void returnBook() throws NoSuchElementException {
        String title = inputBookTitle();
        for (Book book : catalog) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                int copies = inputAvailableCopies();
                book.setAvailableCopies(book.getAvailableCopies() + copies);
                return;
            }
        }
        throw new NoSuchElementException(NO_BOOK_IN_CATALOG);
    }

    public List<Book> getAllBooks() {
        return catalog;
    }

    private String inputBookTitle() {
        String title;
        while (true) {
            System.out.println(INPUT_BOOK_TITLE);
            title = scanner.nextLine();
            if (title.trim().isEmpty()) {
                System.out.println("Значение названия книги пустое. Повторите ввод названия книги снова");
            } else {
                break;
            }
        }
        return title;
    }

    private String inputAuthor() {
        String author;
        while (true) {
            System.out.println(INPUT_AUTHOR);
            author = scanner.nextLine();
            if (author.trim().isEmpty()) {
                System.out.println("Значение автора книги пустое. Повторите ввод автора книги снова");
            } else {
                break;
            }
        }
        return author;
    }

    private int inputAvailableCopies() {
        int availableCopies;
        while (true) {
            try {
                System.out.println("Введите количество экземпляров");
                availableCopies = Integer.parseInt(scanner.nextLine());
                if (availableCopies <= 0) {
                    System.out.println("Значение количества экземпляров книги не может быть меньше 1. " +
                            "Повторите ввод количества экземпляров снова");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели не число. Необходимо повторить ввод количества экземпляров снова");
            }
        }
        return availableCopies;
    }
}
