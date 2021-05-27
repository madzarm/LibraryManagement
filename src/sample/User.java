package sample;

import java.time.LocalDate;
import java.util.Date;

public class User {
    private int ID;
    private String name;
    private String email_address;
    private Book issuedBook;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private String issuedBookName = "/";


    public User(int ID, String name, String email_address) {
        this.ID = ID;
        this.name = name;
        this.email_address = email_address;

    }

    public User(int ID, String name, String email_address, Book issuedBook, LocalDate issueDate, LocalDate returnDate) {
        this.ID = ID;
        this.name = name;
        this.email_address = email_address;
        this.issuedBook = issuedBook;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    public String getIssuedBookName() {
        return issuedBookName;
    }

    public void setIssuedBookName(String issuedBookName) {
        this.issuedBookName = issuedBookName;
    }

    public Book getIssuedBook() {
        return issuedBook;
    }

    public void setIssuedBook(Book issuedBook) {
        this.issuedBook = issuedBook;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

}
