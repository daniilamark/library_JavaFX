package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.DBHandler;
import model.Book;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private Button btnDelete;

    @FXML private Button btnInsert;

    @FXML private Button btnUpdate;

    @FXML private TableColumn<Book, String> colAuthor;

    @FXML private TableColumn<Book, Integer> colId;

    @FXML private TableColumn<Book, Integer> colPages;

    @FXML private TableColumn<Book, String> colTitle;

    @FXML private TableColumn<Book, Integer> colYear;

    @FXML private TextField tfAuthor;

    @FXML private TextField tfId;

    @FXML private TextField tfPages;

    @FXML private TextField tfTitle;

    @FXML private TextField tfYear;

    @FXML private TableView<Book> tvBooks;

    DBHandler dbHandler = new DBHandler();

    @FXML
    void handleButtonAction(ActionEvent event) {
        if(event.getSource() == btnInsert){
            insertRecord();
        }else if (event.getSource() == btnUpdate){
            updateRecord();
        }else if(event.getSource() == btnDelete){
            deleteButton();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBooks();
    }

    public ObservableList<Book> getBooksList()  {
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = dbHandler.getDBConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = "SELECT * FROM book";

        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Book book;
            while(rs.next()) {
                book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getInt("year"), rs.getInt("pages"));

                bookList.add(book);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return bookList;
    }

    public void showBooks() {
        ObservableList<Book> list = getBooksList();

        colId.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<Book, Integer>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<Book, Integer>("pages"));

        tvBooks.setItems(list);
    }

    private void insertRecord(){
        String query = "INSERT INTO book VALUES (" + tfId.getText() + ",'" + tfTitle.getText() + "','" + tfAuthor.getText() + "',"
                + tfYear.getText() + "," + tfPages.getText() + ")";
        dbHandler.executeQuery(query);
        showBooks();
    }

    private void updateRecord(){
        String query = "UPDATE  book SET title  = '" + tfTitle.getText() + "', author = '" + tfAuthor.getText() + "', year = " +
                tfYear.getText() + ", pages = " + tfPages.getText() + " WHERE id = " + tfId.getText() + "";
        dbHandler.executeQuery(query);
        showBooks();
    }
    private void deleteButton(){
        String query = "DELETE FROM book WHERE id =" + tfId.getText() + "";
        dbHandler.executeQuery(query);
        showBooks();
    }
}
