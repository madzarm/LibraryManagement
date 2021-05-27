package sample;

import java.time.LocalDate;
import java.util.Date;

public class Issue {
    private int user_id;
    private int book_id;
    private LocalDate issueDate;
    private LocalDate returnDate;

    public Issue(int user_id, int book_id, LocalDate issueDate, LocalDate returnDate) {
        this.user_id = user_id;
        this.book_id = book_id;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
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
}
