package sample;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Book implements Cloneable{
    private String name;
    private String author;
    private int ID;
    private int issuedByID = -1;
    private LocalDate issueDate = null;
    private LocalDate returnDate = null;
    private String issuedByName = "/";

    public Book(String name, String author, int ID) throws SQLException {
        this.name = name;
        this.author = author;
        this.ID = ID;
    }

    public String getIssuedByName() {
        return issuedByName;
    }

    public void setIssuedByName(String issuedByName) {
        this.issuedByName = issuedByName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIssuedByID() {
        return issuedByID;
    }

    public void setIssuedByID(int issuedByID) {
        this.issuedByID = issuedByID;
    }
}


