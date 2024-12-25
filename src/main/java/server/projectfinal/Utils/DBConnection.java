package server.projectfinal.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This code is written by Salmane Koraichi
 **/
public class DBConnection {
    private static DBConnection instance;
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/Dbjava";
    private final String user = "root";
    private final String password = "";

    private PreparedStatement pstm;
    private ResultSet rs;

    private DBConnection() {
        try{
            //Changer le driver JDBC
            System.out.println("Connecting to database...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
            System.out.println("pushing ...");
            //cree la connexion
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database");
        }catch (Exception e) {
            System.out.println("failed reason : " + e.getMessage());
        }
    }


    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }



    public Connection getConnection() {
        return connection;
    }

}
