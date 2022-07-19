package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// класс для работы с БД
public class DBHandler extends ConfigsDb{
    Connection dbConnection; // соединение

    // метод соединение с бд
    public Connection getDBConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName; // строка подключения к БД
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass); // установление соединения
        return dbConnection;
    }

    // метод выполнение запроса
    public void executeQuery(String query) {
        Connection conn = null; // соединение
        try {
            conn = getDBConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement st; // контейнер для выполнения SQL-выражений через установленное соединение
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
            System.out.println("всё гуд");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
