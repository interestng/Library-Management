import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String title;
    private String author;
    private String isbn;
    
    public Book(int id, String title, String author, String isbn) {
        this.id     = id;
        this.title  = title;
        this.author = author;
        this.isbn   = isbn;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    @Override
    public String toString() {
        return id + "," + title + "," + author + "," + isbn;
    }
    
    // Parse a Book from a CSV string
    public static Book fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) return null;
        try {
            int id = Integer.parseInt(parts[0]);
            return new Book(id, parts[1], parts[2], parts[3]);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
