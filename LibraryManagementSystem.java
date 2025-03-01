import javax.swing.SwingUtilities;
import java.util.List;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        // Load books from file
        List<Book> savedBooks = LibraryPersistence.loadBooks();
        
        // Initialize LibraryManager and set loaded books
        LibraryManager manager = new LibraryManager();
        manager.setBooks(savedBooks);
        
        // Launch the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LibraryGUI gui = new LibraryGUI(manager);
            gui.setVisible(true);
        });
    }
}
