import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class LibraryGUI extends JFrame {
    private LibraryManager manager;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField isbnField;
    private JTextField searchField;
    
    public LibraryGUI(LibraryManager manager) {
        this.manager = manager;
        setTitle("Library Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LibraryPersistence.saveBooks(manager.getBooks());
                System.out.println("Books saved to file.");
            }
        });
    }
    
    private void initComponents() {
        // Table for displaying books
        String[] columns = {"ID", "Title", "Author", "ISBN"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        refreshTable(manager.getBooks());
        JScrollPane tableScroll = new JScrollPane(table);
        
        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        titleField  = new JTextField();
        authorField = new JTextField();
        isbnField   = new JTextField();
        searchField = new JTextField();
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(new JLabel("ISBN:"));
        inputPanel.add(new JLabel("Search by Title:"));
        inputPanel.add(titleField);
        inputPanel.add(authorField);
        inputPanel.add(isbnField);
        inputPanel.add(searchField);
        
        // Buttons
        JButton addButton    = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton searchButton = new JButton("Search");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());
        searchButton.addActionListener(e -> searchBooks());
        refreshButton.addActionListener(e -> refreshTable(manager.getBooks()));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(refreshButton);
        
        // Layout setup
        setLayout(new BorderLayout(10, 10));
        add(tableScroll, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // Refresh table with given list of books
    private void refreshTable(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book b : books) {
            tableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn()});
        }
    }
    
    // Add a new book
    private void addBook() {
        String title  = titleField.getText().trim();
        String author = authorField.getText().trim();
        String isbn   = isbnField.getText().trim();
        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }
        manager.addBook(title, author, isbn);
        refreshTable(manager.getBooks());
        clearInputFields();
    }
    
    // Update selected book
    private void updateBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a book to update.");
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String title  = titleField.getText().trim();
        String author = authorField.getText().trim();
        String isbn   = isbnField.getText().trim();
        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }
        boolean updated = manager.updateBook(id, title, author, isbn);
        if (updated) {
            refreshTable(manager.getBooks());
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(this, "Update failed. Book not found.");
        }
    }
    
    // Delete selected book
    private void deleteBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a book to delete.");
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?");
        if (confirm == JOptionPane.YES_OPTION) {
            manager.deleteBook(id);
            refreshTable(manager.getBooks());
        }
    }
    
    // Search books by title
    private void searchBooks() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a search keyword.");
            return;
        }
        List<Book> results = manager.searchBooks(keyword);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books found.");
        }
        refreshTable(results);
    }
    
    // Clear input fields
    private void clearInputFields() {
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
    }
}
