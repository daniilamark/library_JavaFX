package controller;

// импорт необходимых пакетов
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

// класс-контроллер
public class MainController implements Initializable {
    // компоненты интерфейса главного экрана
    @FXML private Button btnDelete; // кнопка "Удалить"
    @FXML private Button btnInsert; // кнопка "Вставить"
    @FXML private Button btnUpdate; // кнопка "Обновить"
    @FXML private TableColumn<Book, String> colAuthor; // колонка в таблице "Автор"
    @FXML private TableColumn<Book, Integer> colId; // колонка в таблице "Код"
    @FXML private TableColumn<Book, Integer> colPages; // колонка в таблице "Кол-ва страниц"
    @FXML private TableColumn<Book, String> colTitle; // колонка в таблице "Название"
    @FXML private TableColumn<Book, Integer> colYear; // колонка в таблице "Год выпуска"
    @FXML private TextField tfAuthor; // текстовое поле ввода Автора
    @FXML private TextField tfId; // текстовое поле ввода Код
    @FXML private TextField tfPages; // текстовое поле ввода Кол-ва страниц
    @FXML private TextField tfTitle; // текстовое поле ввода Названия
    @FXML private TextField tfYear; // текстовое поле ввода Года выпуска
    @FXML private TableView<Book> tvBooks; // таблица "Книги"

    DBHandler dbHandler = new DBHandler(); // создание объекта класса DBHandler для работы с БД

    // метод обработки событий нажатия на кнопки
    @FXML
    void handleButtonAction(ActionEvent event) {
        if(event.getSource() == btnInsert){
            insertRecord(); // вызов метода - сделать запись
        }else if (event.getSource() == btnUpdate){
            updateRecord(); // вызов метода - обновить запись
        }else if(event.getSource() == btnDelete){
            deleteButton(); // вызов метода - удалить запись
        }
    }

    // переопределение метода инициализации
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBooks(); // вызов метода - показать все книги
    }

    // метод получения списка книг из БД
    public ObservableList<Book> getBooksList()  {
        ObservableList<Book> bookList = FXCollections.observableArrayList(); // список книг
        // соединение с БД
        Connection conn = null;
        try {
            conn = dbHandler.getDBConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // заполение списка данными из БД
        String query = "SELECT * FROM book"; // запрос к БД
        Statement st; // контейнер для выполнения SQL-выражений через установленное соединение
        ResultSet rs; // результирующий набор данных, обеспечивает приложению построчный доступ к результатам запросов
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Book book; // объект класса книга
            while(rs.next()) {
                book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getInt("year"), rs.getInt("pages"));
                bookList.add(book);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return bookList;
    }

    // метод заполния колонок таблицы данными из списка
    public void showBooks() {
        ObservableList<Book> list = getBooksList(); // заполнение списка данными из БД
        colId.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<Book, Integer>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<Book, Integer>("pages"));
        tvBooks.setItems(list); // отображение данных в таблице на экране
    }

    // метод записи книги в БД
    private void insertRecord(){
        // запрос
        String query = "INSERT INTO book VALUES (" + tfId.getText() + ",'" + tfTitle.getText() + "','" + tfAuthor.getText() + "',"
                + tfYear.getText() + "," + tfPages.getText() + ")";
        dbHandler.executeQuery(query); // выполнение запроса
        showBooks(); // обновление отображение данных в таблице на экране
    }

    // метод обновления записи книги в БД
    private void updateRecord(){
        // запрос
        String query = "UPDATE  book SET title  = '" + tfTitle.getText() + "', author = '" + tfAuthor.getText() + "', year = " +
                tfYear.getText() + ", pages = " + tfPages.getText() + " WHERE id = " + tfId.getText() + "";
        dbHandler.executeQuery(query); // выполнение запроса
        showBooks(); // обновление отображение данных в таблице на экране
    }
    // метод удаления книги из БД
    private void deleteButton(){
        // запрос
        String query = "DELETE FROM book WHERE id =" + tfId.getText() + "";
        dbHandler.executeQuery(query);// выполнение запроса
        showBooks(); // обновление отображение данных в таблице на экране
    }
}
