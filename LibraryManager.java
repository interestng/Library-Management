import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibraryManager {
    private List<Book> books;
    private int nextId;
    
    public LibraryManager() {
        books = new ArrayList<>();
        nextId = 1;
    }
    
    public List<Book> getBooks() {
        return books;
    }
    
    // Adds a new book and assigns a unique ID
    public void addBook(String title, String author, String isbn) {
        Book book = new Book(nextId++, title, author, isbn);
        books.add(book);
    }
    
    // Updates an existing book (returns true if successful)
    public boolean updateBook(int id, String title, String author, String isbn) {
        Optional<Book> opt = books.stream().filter(b -> b.getId() == id).findFirst();
        if (opt.isPresent()) {
            Book book = opt.get();
            book.setTitle(title);
            book.setAuthor(author);
            book.setIsbn(isbn);
            return true;
        }
        return false;
    }
    
    // Deletes a book by its ID (returns true if found and removed)
    public boolean deleteBook(int id) {
        return books.removeIf(b -> b.getId() == id);
    }
    
    // Searches for books by title (case-insensitive)
    public List<Book> searchBooks(String keyword) {
        List<Book> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lowerKeyword)) {
                results.add(book);
            }
        }
        return results;
    }
    
    // Replace all books (useful when loading from file)
    public void setBooks(List<Book> newBooks) {
        books = newBooks;
        nextId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;
    }
}
