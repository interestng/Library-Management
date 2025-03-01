import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryPersistence {
    private static final String FILENAME = "books.csv";
    
    // Save the list of books to a file
    public static void saveBooks(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Book book : books) {
                writer.write(book.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }
    
    // Load books from the file and return a list
    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        File file = new File(FILENAME);
        if (!file.exists()) {
            return books;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Book book = Book.fromString(line);
                if (book != null) {
                    books.add(book);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }
        return books;
    }
}
