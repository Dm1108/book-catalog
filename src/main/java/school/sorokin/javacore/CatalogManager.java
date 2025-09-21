package school.sorokin.javacore;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CatalogManager {

    private static final Scanner scanner = new Scanner(System.in);
    private List<Book> catalog = new ArrayList<>();
    private Logger logger = Logger.getLogger(CatalogManager.class.getName());
    private static final String INPUT_BOOK_TITLE = "Введите название книги: ";
    private static final String NO_BOOK_IN_CATALOG = "Нет такой книги в каталоге";
    private static final String INPUT_AUTHOR = "Введите автора: ";
    private static final String YOU_INPUT_BOOK_TITLE = "Вы ввели название книги: ";

    public void bookMenu() {
        int pointOfMenu;
        while (true) {
            System.out.println("""
                    "Выберите пункт меню:
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
        System.out.println(INPUT_BOOK_TITLE);
        String title = scanner.nextLine();
        System.out.println(INPUT_AUTHOR);
        String author = scanner.nextLine();
        System.out.println("Введите количество экземпляров");
        int availableCopies = Integer.parseInt(scanner.nextLine());
        for (Book book : catalog) {
            if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) {
                book.setAvailableCopies(book.getAvailableCopies() + availableCopies);
                return;
            }
        }
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setAvailableCopies(availableCopies);
        catalog.add(newBook);
    }


    public void takeBook() throws NoAvailableCopiesException, NoSuchElementException {
        System.out.println(INPUT_BOOK_TITLE);
        String title = scanner.nextLine();
        for (Book ex : catalog) {
            if (ex.getTitle().equalsIgnoreCase(title) && ex.getAvailableCopies() != 0) {
                System.out.println("Введите количество экземпляров книги сколько хотите взять: ");
                int takeBookNumber = Integer.parseInt(scanner.nextLine());
                if (ex.getAvailableCopies() >= takeBookNumber) {
                    ex.setAvailableCopies(ex.getAvailableCopies() - takeBookNumber);
                    break;
                } else {
                    System.out.println("В каталоге нет столько экземпляров книги " + ex.getTitle());
                    System.out.println("Количество экземпляров книги " + ex.getTitle() + " = " + ex.getAvailableCopies());
                }
            }
            if (ex.getTitle().equalsIgnoreCase(title) && ex.getAvailableCopies() == 0) {
                System.out.println(YOU_INPUT_BOOK_TITLE + title);
                throw new NoAvailableCopiesException("Сейчас нет экземпляра этой книги в каталоге. Она выдана");
            }
            if (!ex.getTitle().equalsIgnoreCase(title)) {
                System.out.println(YOU_INPUT_BOOK_TITLE + title);
                throw new NoSuchElementException(NO_BOOK_IN_CATALOG);
            }
        }
    }

    public void returnBook() throws NoSuchElementException {
        System.out.println(INPUT_BOOK_TITLE);
        String title = scanner.nextLine();
        for (Book book : catalog) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Сколько экземпляров книги хотите вернуть: ");
                int copies = Integer.parseInt(scanner.nextLine());
                book.setAvailableCopies(book.getAvailableCopies() + copies);
                break;
            } else {
                System.out.println(YOU_INPUT_BOOK_TITLE + title);
                throw new NoSuchElementException(NO_BOOK_IN_CATALOG);
            }
        }
    }

    public List<Book> getAllBooks() {
        return catalog;
    }
}
