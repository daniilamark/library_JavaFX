package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHandler extends ConfigsDb{
    Connection dbConnection;

    // метод соединение с бд
    public Connection getDBConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    // метод выполнение запроса
    public void executeQuery(String query) {
        Connection conn = null;
        try {
            conn = getDBConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
            System.out.println("всё гуд");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
